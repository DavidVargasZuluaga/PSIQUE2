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
import javax.faces.application.FacesMessage;
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
    private UsuarioFacade ejbUsuario;
    
    private Usuario usuarioLog;
    private List<Usuario> listaUsuarios;
    
    @PostConstruct
    public void init(){
        usuarioLog = new Usuario();
        listaUsuarios = new ArrayList();
    }
    
    public String autenticar() {
        String res = "/index.ndvz";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            Long doc = Long.parseLong((String) params.get("documento"));
            String clave = (String) params.get("clave");
            this.listaUsuarios = ejbUsuario.findAll();
            for (int i = 0; i < listaUsuarios.size(); i++) {
                if (listaUsuarios.get(i).getNoDocumento() == doc && listaUsuarios.get(i).getClave().equals(clave)) {
                    this.usuarioLog = listaUsuarios.get(i);
                    httpServletRequest.getSession().setAttribute("UsuarioLog", listaUsuarios.get(i));
                    if (listaUsuarios.get(i).getIdRol().getIdRol()==1) {
                        res = "/Admon/admon.ndvz";
                    } else {
                        res = "/cuenta";
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
     
    
}
