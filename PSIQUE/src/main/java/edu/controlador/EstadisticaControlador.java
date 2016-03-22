/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author DavidBrootal
 */
@Named(value = "estadisticaControlador")
@SessionScoped
public class EstadisticaControlador implements Serializable {

    @Inject
    AprendizFacade aprendizFacade;

    private List<Aprendiz> listaAprendices;

    @PostConstruct
    public void init() {
        listaAprendices = new ArrayList();
    }

    public ArrayList<Integer> contarOrientacionSexual() {
        ArrayList<Integer> listaEstadistica = new ArrayList();
        listaAprendices = aprendizFacade.findAll();
        int homosexual = 0;
        int heterosexual = 0;
        int bisexual = 0;
        int asexual = 0;
        for (int i = 0; i < listaAprendices.size(); i++) {
            switch (listaAprendices.get(i).getOrientacionSexual()) {
                case "Homosexual":
                    homosexual++;
                    break;
                case "Heterosexual":
                    heterosexual++;
                    break;

                case "Bisexual":
                    bisexual++;
                    break;
                case "Asexual":
                    asexual++;
                    break;
            }
        }
        listaEstadistica.add(heterosexual);
        listaEstadistica.add(homosexual);
        listaEstadistica.add(bisexual);
        listaEstadistica.add(asexual);
        return listaEstadistica;
    }

}
