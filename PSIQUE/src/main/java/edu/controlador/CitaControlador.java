package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    @PostConstruct
    private void init() {
        citaTemp = new Cita();
    }

    public String valorarCita() {
        citaFacade.edit(citaTemp);
        return "/citaProgramada.xhtml";
    }

    public String solicitarCita(Aprendiz a) {
        citaTemp = new Cita();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            citaTemp.setIdAprendiz(a);
            citaTemp.setIdPsicologo(psicologoFacade.find(Long.parseLong((String) params.get("psicologo"))));
            citaTemp.setFecha(format.parse((String) params.get("fecha")));
            citaTemp.setEstado("SOLICITADA");
            citaFacade.create(citaTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/modAprendiz/citaProgramada.xhtml";
    }
    
    public String cancelarCitaAprendiz(Cita c){
        citaTemp = citaFacade.find(c.getIdCita());
        citaTemp.setEstado("CANCELADA");
        citaFacade.edit(citaTemp);
        return "/modAprendiz/citaProgramada.xhtml";
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

}
