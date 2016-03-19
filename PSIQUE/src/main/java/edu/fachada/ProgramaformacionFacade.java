/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fachada;

import edu.entidad.Programaformacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DavidBrootal
 */
@Stateless
public class ProgramaformacionFacade extends AbstractFacade<Programaformacion> {

    @PersistenceContext(unitName = "PSIQUEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramaformacionFacade() {
        super(Programaformacion.class);
    }
    
}
