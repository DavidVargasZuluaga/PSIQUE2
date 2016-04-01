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

    @Inject
    private UsuarioFacade usuarioFacade;

    private Date fechaActual;

    private int modalMensaje;
    private Mensaje mensajeTemp;
    private List<Mensaje> mensajes;
    private List<Usuario> listaUsuarios;

    @PostConstruct
    private void init() {
        modalMensaje = 0;
        mensajeTemp = new Mensaje();
        fechaActual = new Date();
        mensajes = mensajeFacade.findAll();
        listaUsuarios = usuarioFacade.findAll();
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

    public String borrarMensajeAprendiz(Mensaje m) {
        String res = "/modAprendiz/mensajeria.xhtml";
        try {
            m.setEstado("Borrado");
            mensajeFacade.edit(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public String enviarMensaje(Usuario us) {
        String res = " ";
        Mensaje m = new Mensaje();
        String espacion = " ";
        fechaActual = new Date();
        Usuario receptor = new Usuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            String rec = (String) params.get("para");
            for (int i = 0; i < listaUsuarios.size(); i++) {
                String nombre = (listaUsuarios.get(i).getNombres()+""+ listaUsuarios.get(i).getPrimerApellido() +""+ listaUsuarios.get(i).getSegundoApellido());
                if (rec.equals(nombre)) {
                    receptor = listaUsuarios.get(i);
                    m.setIdEmisor(us);
                    m.setFecha(fechaActual);
                    m.setIdReceptor(receptor);
                    m.setEstado("Enviado");
                    m.setMensaje((String) params.get("mensaje"));
                    m.setAsunto((String) params.get("asunto"));
                    mensajeFacade.create(m);
                    break;
                }
            }
            if (receptor.getIdUsuario() == null) {
                modalMensaje = 1;
            }
            switch (us.getIdRol().getIdRol()) {
                case 1:
                    res = "/modAdmon/mensajeria.xhtml";
                    break;
                case 2:
                    res = "/modCoordinador/mensajeria.xhtml";
                    break;
                case 3:
                    res = "/modPsicologo/mensajeria.xhtml";
                    break;
                case 4:
                    res = "/modAprendiz/mensajeria.xhtml";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
            String me = "Enviado";
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

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

}
