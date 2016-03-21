package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "usuarioControlador")
@SessionScoped
public class UsuarioControlador implements Serializable {

    @Inject
    UsuarioFacade ejbUsuario;

    @Inject
    AprendizFacade aprendizFacade;

    @Inject
    PsicologoFacade psicologoFacade;

    @Inject
    CitaFacade citaFacade;
    

    private Usuario usuarioLog;
    private Aprendiz aprendizLog;
    private List<Usuario> listaUsuarios;
    private Psicologo psicologoLog;

    @PostConstruct
    public void init() {
        usuarioLog = new Usuario();
        listaUsuarios = new ArrayList();
        aprendizLog = new Aprendiz();
        psicologoLog = new Psicologo();
    }

    public String autenticar() {
        String res = "/index.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            Long doc = Long.parseLong((String) params.get("documento"));
            String clave = (String) params.get("clave");
            this.listaUsuarios = ejbUsuario.findAll();
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getIdUsuario().equals(doc) && listaUsuarios.get(i).getClave().equals(clave)) {
                    this.usuarioLog = listaUsuarios.get(i);
                    httpServletRequest.getSession().setAttribute("UsuarioLog", listaUsuarios.get(i));
                    break;
                }
            }
            switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "/index.xhtml";
                    break;
                case 2:
                    res = "/index.xhtml";
                    break;
                case 3:
                    psicologoLog = psicologoFacade.find(usuarioLog.getIdUsuario());
                    res = "modPsicologo/indexPsicologo.xhtml";
                    break;
                case 4:
                    aprendizLog = aprendizFacade.find(usuarioLog);
                    res = "modAprendiz/principalAprendiz.xhtml";
                    break;
                default:
                    res = "/index.xhtml";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public String cancelarCita (Cita cita){
        cita.setEstado("cancelada");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }
    
    public String aceptarCitar (Cita cita){
          cita.setEstado("pendiente");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }
    
    public List<Aprendiz> mostrarAprendices() {
        List<Aprendiz> aprendices = aprendizFacade.findAll();
        return aprendices;
    }

    public List<Cita> mostrarCitasPendientes() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("pendiente")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public List<Cita> mostrarCitasSolicitadas() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("SOLICITADA")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public List<Cita> mostrarCitas() {
        List<Cita> Citas = citaFacade.findAll();
        return Citas;
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Aprendiz getAprendizLog() {
        return aprendizLog;
    }

    public void setAprendizLog(Aprendiz aprendizLog) {
        this.aprendizLog = aprendizLog;
    }

}
