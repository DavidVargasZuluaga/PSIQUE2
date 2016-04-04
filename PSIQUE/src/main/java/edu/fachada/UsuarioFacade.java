/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fachada;

import edu.entidad.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DavidBrootal
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "PSIQUEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario traerContrasena(String usu) {
        Usuario usuario = null;
        String consulta;
        try {
            consulta = "SELECT u FROM Usuario u WHERE u.correo = :correo";
            Query query = em.createQuery(consulta);
            query.setParameter("correo", usu);
            List<Usuario> lista= query.getResultList();
            if (!lista.isEmpty()) {
                usuario =  lista.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return usuario;
    }

}
