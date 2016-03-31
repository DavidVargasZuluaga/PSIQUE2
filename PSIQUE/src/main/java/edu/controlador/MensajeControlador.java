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
@Named(value = "mensajeControlador")
@SessionScoped
public class MensajeControlador implements Serializable {

    @Inject
    private MensajeFacade mensajeFacade;

    private Date fechaActual;

    private Mensaje mensajeTemp;

    @PostConstruct
    private void init() {
        mensajeTemp = new Mensaje();
        fechaActual = new Date();
    }

    public List<Mensaje> cargarMensajesRecibidos(Usuario u) {
        List<Mensaje> tMensajes = mensajeFacade.findAll();
        List<Mensaje> mensajes = new ArrayList();
        try {
            for (int i = 0; i < tMensajes.size(); i++) {
                if (tMensajes.get(i).getIdReceptor().getIdUsuario().equals(u.getIdUsuario())) {
                    if (!tMensajes.get(i).getEstado().equals("Borrado")) {
                        mensajes.add(tMensajes.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public List<Mensaje> cargarMensajesEnviados(Usuario u) {
        List<Mensaje> tMensajes = mensajeFacade.findAll();
        List<Mensaje> mensajes = new ArrayList();
        try {
            for (int i = 0; i < tMensajes.size(); i++) {
                if (tMensajes.get(i).getIdEmisor().getIdUsuario().equals(u.getIdUsuario())) {
                    mensajes.add(tMensajes.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public List<Mensaje> cargarMensajesEliminador(Usuario u) {
        List<Mensaje> tMensajes = mensajeFacade.findAll();
        List<Mensaje> mensajes = new ArrayList();
        try {
            for (int i = 0; i < tMensajes.size(); i++) {
                if (tMensajes.get(i).getIdReceptor().getIdUsuario().equals(u.getIdUsuario())) {
                    if (tMensajes.get(i).getEstado().equals("Borrado")) {
                        mensajes.add(tMensajes.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }

    public String reenviarMensaje(Usuario ue, Usuario ur) {
        String res = "";
        Mensaje m = new Mensaje();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            m.setIdEmisor(ur);
            m.setIdReceptor(ue);
            m.setFecha(fechaActual);
            String me = "ENVIADA";
            m.setEstado(me);
            m.setAsunto((String) params.get("asunto"));
            m.setMensaje((String) params.get("mensaje"));
            mensajeFacade.create(m);
            res = "/modAprendiz/mensajeria.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Mensaje getMensajeTemp() {
        return mensajeTemp;
    }

    public void setMensajeTemp(Mensaje mensajeTemp) {
        this.mensajeTemp = mensajeTemp;
    }

}
