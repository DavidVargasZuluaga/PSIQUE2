package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "citaControlador")
@SessionScoped
public class CitaControlador implements Serializable {

    @Inject
    private CitaFacade citaFacade;
    
    private Cita citaTemp;
    
    @PostConstruct
    private void init(){
        citaTemp = new Cita();
    }
    
    public String valorarCita(){
        citaFacade.edit(citaTemp);
        return "/citaProgramada.xhtml";
    }

    public Cita getCitaTemp() {
        return citaTemp;
    }

    public void setCitaTemp(Cita citaTemp) {
        this.citaTemp = citaTemp;
    }
    
}
