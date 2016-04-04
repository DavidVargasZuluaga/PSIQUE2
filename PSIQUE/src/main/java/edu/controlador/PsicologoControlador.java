package edu.controlador;

import edu.entidad.*;
import edu.fachada.*;
import java.io.IOException;
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
@Named(value = "psicologoControlador")
@SessionScoped
public class PsicologoControlador implements Serializable {

    @Inject
    private UsuarioFacade ejbUsuario;

    @Inject
    private AprendizFacade aprendizFacade;

    @Inject
    private PsicologoFacade psicologoFacade;

    @Inject
    private CitaFacade citaFacade;

    @Inject
    private TestFacade testFacade;

    @Inject
    private PreguntaFacade preguntaFacade;

    @Inject
    private RespuestaFacade respuestaFacade;
    
    @Inject 
    private FichaFacade fichaFacade;
   

    private Usuario usuarioLog;
    private Aprendiz aprendizLog;
    private List<Usuario> listaUsuarios;
    private Psicologo psicologoLog;
    private Test testLog;
    private Pregunta preguntaLog;
    private Respuesta respuestaLog;
    private Ficha fichaLog;
    private int estados;

    @PostConstruct
    public void init() {
        usuarioLog = new Usuario();
        listaUsuarios = new ArrayList();
        aprendizLog = new Aprendiz();
        psicologoLog = new Psicologo();
        testLog = new Test();
        preguntaLog = new Pregunta();
        respuestaLog = new Respuesta();
        fichaLog = new Ficha();
    }

    public String autenticar() {
        String res = "/index.xhtml";
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
                    break;
                }
            }
            switch (usuarioLog.getIdRol().getIdRol()) {
                case 1:
                    res = "/modPsicologo/indexPsicologo.xhtml";
                    break;
                case 2:
                    res = "modCoordinador/principalCoordinador.xhtml";
                    break;
                case 3:
                    psicologoLog = psicologoFacade.find(usuarioLog.getIdUsuario());
                    res = "modPsicologo/indexPsicologo.xhtml";
                    break;
                case 4:
                    aprendizLog = aprendizFacade.find(usuarioLog.getIdUsuario());
                    res = "modAprendiz/principalAprendiz.xhtml";
                    break;
                default:
                    res = "/index.xhtml";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public List<Ficha> mostrarFichas (){
        return fichaFacade.findAll();
    }
    
    public List<Aprendiz> mostrarAprendicesFicha (){
        return (List<Aprendiz>) fichaLog.getAprendizCollection();
    }

    public String crearTest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Date fecha = new Date();
        Test test = new Test();
        test.setIdTest(null);
        test.setIdAprendiz(null);
        test.setResultado(null);
        test.setFecha(fecha);
        test.setNombre((String) params.get("titulo"));
        test.setDescripcion((String) params.get("descripcion"));
        testFacade.create(test);
        estados = 1;
        return "/modPiscologo/crearTest.xhtml";
    }

    public List<Test> mostrarTest() {
        return testFacade.findAll();
    }

    public void crearRespuesta() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        int valor = Integer.parseInt((String) params.get("valor"));
        Respuesta respuesta = new Respuesta();
        respuesta.setIdRespuesta(null);
        respuesta.setIdPregunta(preguntaLog);
        respuesta.setTexto((String) params.get("texto"));
        respuesta.setValor(valor);
        respuestaFacade.create(respuesta);
        preguntaLog.getRespuestaCollection().add(respuesta);
        estados = 1;
    }
    
    public void eliminarRespuesta(Respuesta respuesta){
        respuestaFacade.remove(respuesta);
    }

    public String crearPregunta() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Pregunta pregunta = new Pregunta();
        pregunta.setIdPregunta(null);
        pregunta.setIdTest(testLog);
        pregunta.setTexto((String) params.get("pregunta"));
        pregunta.setRespuesta(Integer.parseInt((String)params.get("respuesta")));
        preguntaFacade.create(pregunta);
        testLog.getPreguntaCollection().add(pregunta);
        estados = 1;

        return "/modPiscologo/crearPregunta.xhtml";
    }
    
    public void asignarTest (Test test){
        test.setIdTest(null);
        test.setIdAprendiz(aprendizLog);
        testFacade.create(test);
    }

    public List<Respuesta> mostrarRespuestas() {
        return (List<Respuesta>) preguntaLog.getRespuestaCollection();
        
    }

    public void eliminarPregunta(Pregunta pregunta) {
        preguntaFacade.remove(pregunta);
    }

    public String cerrarSesion() {
        try {
            init();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/index";
    }

    public List<Pregunta> mostrarPreguntas() {
        return (List<Pregunta>) testLog.getPreguntaCollection();
    }

    public void validarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            if (httpServletRequest.getSession().getAttribute("UsuarioLog") != null) {
            } else {
                facesContext.getExternalContext().redirect("/PSIQUE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String cancelarCita(Cita cita) {
        cita.setEstado("cancelada");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }

    public String aceptarCitar(Cita cita) {
        cita.setEstado("pendiente");
        citaFacade.edit(cita);
        return "citasSolicitadas.xhtml";
    }

    public List<Aprendiz> mostrarAprendices() {
        List<Aprendiz> aprendices = aprendizFacade.findAll();
        return aprendices;
    }

    public String editarTest() {

        testFacade.edit(testLog);
        estados = 1;
        return "/modPiscologo/editarTest.xhtml";
    }

    public String editarPregunta() {
        estados = 1;
        preguntaFacade.edit(preguntaLog);
        return "/modPiscologo/editarPregunta.xhtml";
    }

    public String editarRespuesta() {
        estados = 1;
        respuestaFacade.edit(respuestaLog);
        return "/modPiscologo/editarRespuesta.xhtml";
    }

    public List<Cita> mostrarCitasPendientes() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("pendiente")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public List<Cita> mostrarCitasSolicitadas() {
        List<Cita> Citas = citaFacade.findAll();
        List<Cita> citasPendientes = new ArrayList<Cita>();
        for (int i = 0; i < Citas.size(); i++) {
            if (Citas.get(i).getEstado().equals("SOLICITADA")) {
                citasPendientes.add(Citas.get(i));
            }
        }
        return citasPendientes;
    }

    public String redioreccionarTest() {
        estados = 0;
        return "/modPiscologo/editarTest.xhtml";
    }

    public List<Cita> mostrarCitas() {
        List<Cita> Citas = citaFacade.findAll();
        return Citas;
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Aprendiz getAprendizLog() {
        return aprendizLog;
    }

    public void setAprendizLog(Aprendiz aprendizLog) {
        this.aprendizLog = aprendizLog;
    }

    public Test getTestLog() {
        return testLog;
    }

    public void setTestLog(Test testLog) {
        this.testLog = testLog;
    }

    public Pregunta getPreguntaLog() {
        return preguntaLog;
    }

    public void setPreguntaLog(Pregunta preguntaLog) {
        this.preguntaLog = preguntaLog;
    }

    public int getEstados() {
        return estados;
    }

    public void setEstados(int estados) {
        this.estados = estados;
    }

    public Respuesta getRespuestaLog() {
        return respuestaLog;
    }

    public void setRespuestaLog(Respuesta respuestaLog) {
        this.respuestaLog = respuestaLog;
    }

    public Ficha getFichaLog() {
        return fichaLog;
    }

    public void setFichaLog(Ficha fichaLog) {
        this.fichaLog = fichaLog;
    }
    
    

}