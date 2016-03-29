package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Named(value = "citaControlador")
@SessionScoped
public class CitaControlador implements Serializable {

    @Inject
    private CitaFacade citaFacade;

    @Inject
    private PsicologoFacade psicologoFacade;

    private Cita citaTemp;
    private Cita citaAnterior;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mma");

    private int modalCita;
    
    @PostConstruct
    private void init() {
        citaTemp = new Cita();
        
        modalCita = 0;
    }

    public List<Cita> listarCitasAprendizLog(Aprendiz a) {
        List<Cita> resul = new ArrayList();
        List<Cita> citas = citaFacade.findAll();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                resul.add(citas.get(i));
            }
        }
        return resul;
    }

    public String valorarCita(Cita cita) {
        Cita c = cita;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        c.setValoracion(Integer.parseInt((String) params.get("valor")));
        citaFacade.edit(c);
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public String solicitarCita(Aprendiz a) {
        citaTemp = new Cita();
        boolean existe = false;
        List<Cita> listaCitas = citaFacade.findAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            citaTemp.setIdAprendiz(a);
            citaTemp.setIdPsicologo(psicologoFacade.find(Long.parseLong((String) params.get("psicologo"))));
            String hora;
            if(citaTemp.getIdPsicologo().getJornada().equals("Mañana")){
                hora = (params.get("fecha2")+" "+params.get("hora2"));
            } else {
                hora = (params.get("fecha")+" "+params.get("hora"));
            }
            citaTemp.setFecha((Date) format.parse(hora));
            citaTemp.setValoracion(0);
            citaTemp.setEstado("SOLICITADA");
            for (int i = 0; i < listaCitas.size() ; i++) {
                if(listaCitas.get(i).getFecha().equals(citaTemp.getFecha())){
                    existe = true;
                    break;
                }
            }
            if(!existe){
                modalCita = 0;
                citaFacade.create(citaTemp);
            } else {
                modalCita = 1;
                citaTemp = new Cita();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public String cancelarCitaAprendiz(Cita c) {
        citaTemp = citaFacade.find(c.getIdCita());
        citaTemp.setEstado("CANCELADA");
        citaFacade.edit(citaTemp);
        return "/modAprendiz/citaProgramada.xhtml";
    }

    public int confirmacionSolicitud(Aprendiz a) {
        int res = 0;
        List<Cita> listaCitas = citaFacade.findAll();
        List<Cita> citas = new ArrayList();
        try {
            for (int i = 0; i < listaCitas.size(); i++) {
                if (listaCitas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz())) {
                    citas.add(listaCitas.get(i));
                }
            }
            for (int i = 0; i < citas.size(); i++) {
                switch (citas.get(i).getEstado()) {
                    case "PENDIENTE":
                        res = 1;
                        break;
                    case "SOLICITADA":
                        res = 1;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Cita ultimaCitaAprendiz(Aprendiz a) {
        citaTemp = new Cita();
        List<Cita> listaCitas = citaFacade.findAll();
        for (int i = 0; i < listaCitas.size(); i++) {
            if (listaCitas.get(i).getIdAprendiz().getIdAprendiz().equals(a.getIdAprendiz()) && listaCitas.get(i).getEstado().equals("CUMPLIDA")) {
                citaTemp = listaCitas.get(i);
            }
        }
        if(citaTemp.getIdCita() == null){
            citaTemp = null;
        }
        return citaTemp;
    }

    public Cita getCitaTemp() {
        return citaTemp;
    }

    public void setCitaTemp(Cita citaTemp) {
        this.citaTemp = citaTemp;
    }

    public Cita getCitaAnterior() {
        return citaAnterior;
    }

    public void setCitaAnterior(Cita citaAnterior) {
        this.citaAnterior = citaAnterior;
    }

    public int getModalCita() {
        return modalCita;
    }

    public void setModalCita(int modalCita) {
        this.modalCita = modalCita;
    }

}
