/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fachada;

import edu.entidad.Aprendiz;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DavidBrootal
 */
@Stateless
public class AprendizFacade extends AbstractFacade<Aprendiz> {

    @PersistenceContext(unitName = "PSIQUEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AprendizFacade() {
        super(Aprendiz.class);
    }
    
}
