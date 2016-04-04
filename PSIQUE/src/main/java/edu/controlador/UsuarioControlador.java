package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private RolFacade rolFacade;

    @Inject
    private UsuarioFacade usuarioFacade;

    @Inject
    private AprendizFacade aprendizFacade;

    @Inject
    private PsicologoFacade psicologoFacade;

    @Inject
    private CitaFacade citaFacade;
    
    @Inject
    private FichaFacade fichaFacade;

    private int modalCreacion;
    private Date fechaActual = new Date();
    
    private Usuario usuarioLog;
    private Usuario usuarioTemp;
    private Aprendiz aprendizLog;
    private Aprendiz aprendizTemp;
    private Psicologo psicologoLog;
    
    private List<Usuario> listaUsuarios;
    private List<Psicologo> listaPsicologos;

    @PostConstruct
    public void init() {
        modalCreacion = 0;
        
        usuarioLog = new Usuario();
        usuarioTemp = new Usuario();
        aprendizLog = new Aprendiz();
        aprendizTemp = new Aprendiz();
        psicologoLog = new Psicologo();
        
        listaUsuarios = usuarioFacade.findAll();
        listaPsicologos = psicologoFacade.findAll();
    }
    
    public Usuario buscarPorNombre(String nombre){
        List<Usuario> listaU = usuarioFacade.findAll();
        Usuario us = new Usuario();
        for (int i = 0; i < listaU.size(); i++) {
            if(listaU.get(i).getNombres().equals(nombre) || listaU.get(i).getPrimerApellido().equals(nombre)){
                us = listaU.get(i);
                break;
            }
        }
        return us;
    }
    
    public String autenticar() {
        String res = "/PSIQUE/index.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            Long doc = Long.parseLong((String) params.get("documento"));
            String clave = (String) params.get("clave");
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getNoDocumento() == doc && listaUsuarios.get(i).getClave().equals(clave)) {
                    this.usuarioLog = listaUsuarios.get(i);
                    httpServletRequest.getSession().setAttribute("UsuarioLog", listaUsuarios.get(i));
                    break;
                }
            }
            switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "/modAdmon/miperfilAd.xhtml";
                    break;
                case 2:
                    res = "/modCoordinador/principalCoordinador.xhtml";
                    break;
                case 3:
                    res = "/modPsicologo/indexPsicologo.xhtml";
                    break;
                case 4:
                    res = "/modAprendiz/principalAprendiz.xhtml";
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

    public String cerrarSesion() {
        try {
            init();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }

    public void validarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearUsuario() {
        usuarioTemp = new Usuario();
        boolean existe = true;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            usuarioTemp.setIdRol(rolFacade.find(4));
            usuarioTemp.setTipoDocumento((String) params.get("tipoDocumento"));
            usuarioTemp.setNoDocumento(Long.parseLong((String) params.get("documento")));
            usuarioTemp.setClave((String) params.get("clave"));
            usuarioTemp.setClave((String) params.get("correo"));
            usuarioTemp.setEstado("ACTIVO");
            usuarioTemp.setFechaNacimiento((Date) params.get("fechaNacimiento"));
            usuarioTemp.setNombres((String) params.get("nombres"));
            usuarioTemp.setPrimerApellido((String) params.get("primerApellido"));
            usuarioTemp.setSegundoApellido((String) params.get("segundoApellido"));
            usuarioTemp.setTelefono((String) params.get("telefono"));
            for (int i = 0; i < usuarioFacade.findAll().size() ; i++) {
                if(usuarioFacade.findAll().get(i).getNoDocumento() == usuarioTemp.getNoDocumento()){
                    existe = false;
                    usuarioTemp = new Usuario();
                    break;
                }
            }
            if(existe){
                usuarioFacade.create(usuarioTemp);
                modalCreacion = 1;
            }else{
                modalCreacion = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String crearAprendiz(){
        crearUsuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        
        try {
            if(usuarioTemp != null){
                aprendizTemp.setUsuario(usuarioTemp);
                aprendizTemp.setFicha(fichaFacade.find((Integer) params.get("Ficha")));
                aprendizTemp.setUbicacion((String) params.get("ubicacion"));
                aprendizTemp.setSexo((String) params.get("sexo"));
                aprendizTemp.setEstadoCivil((String) params.get("estadoCivil")); 
                aprendizTemp.setRaza((String) params.get("raza")); 
                aprendizTemp.setReligion((String) params.get("religion"));
                aprendizTemp.setTendenciaPolitica((String) params.get("politica")); 
                aprendizTemp.setOrientacionSexual((String) params.get("sexual"));
                aprendizTemp.setDiscapacidad((String) params.get("discapacidad")); 
                aprendizTemp.setPasaTiempo((String) params.get("pasaTiempo"));
                aprendizFacade.create(aprendizTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }
    
    public String editarDatosPersonales(){
        String res = "/PSIQUE";
        usuarioFacade.edit(usuarioLog);
        switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "/index.xhtml";
                    break;
                case 2:
                    res = "/modCoordinador/configuracion.xhtml";
                    break;
                case 3:
                    res = "/modPsicologo/configuracion.xhtml";
                    break;
                case 4:
                    res = "/modAprendiz/configuracion.xhtml";
                    break;
                default:
                    res = "/index.xhtml";
                    break;
            }
        
        return "";
    }
    
    // FELIPE ES UN PUERCO METIENDO CONTENIDO DEL MODULO CITAS EN ESTE CONTROLADOR
    // PENDIENTE POR MODIFICAR

    public String cancelarCita(Cita cita) {
        cita.setEstado("CANCELADA");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }

    public String aceptarCitar(Cita cita) {
        cita.setEstado("PENDIENTE");
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
            if (Citas.get(i).getEstado().equals("PENDIENTE")) {
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

    public int getModalCreacion() {
        return modalCreacion;
    }

    public void setModalCreacion(int modalCreacion) {
        this.modalCreacion = modalCreacion;
    }

    public Usuario getUsuarioTemp() {
        return usuarioTemp;
    }

    public void setUsuarioTemp(Usuario usuarioTemp) {
        this.usuarioTemp = usuarioTemp;
    }

    public Aprendiz getAprendizTemp() {
        return aprendizTemp;
    }

    public void setAprendizTemp(Aprendiz aprendizTemp) {
        this.aprendizTemp = aprendizTemp;
    }

    public Psicologo getPsicologoLog() {
        return psicologoLog;
    }

    public void setPsicologoLog(Psicologo psicologoLog) {
        this.psicologoLog = psicologoLog;
    }

    public List<Psicologo> getListaPsicologos() {
        return listaPsicologos;
    }

    public void setListaPsicologos(List<Psicologo> listaPsicologos) {
        this.listaPsicologos = listaPsicologos;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

}
