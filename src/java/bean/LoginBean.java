/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.RolDao;
import dao.RolDaoImplement;
import dao.UbicacionDao;
import dao.UbicacionDaoImplement;
import dao.UsuarioDao;
import dao.UsuarioDaoImplement;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.Rol;
import model.Ubicacion;
import model.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author andre
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Logger logger = LogManager.getLogger(LoginBean.class);

    private Usuario usuarioLog;
    private Usuario usuarioReg;
    private Usuario empleadoReg;
    private UsuarioDao usuarioDao;
    private Usuario usuarioModificar;
    private boolean aceptaPoliticas;
    //Foto
    private String pathImages = "";
    private String destination = "/images/";
    //Foto Cliente
    private UploadedFile uploadedFile;
    private StreamedContent clientImage = null;
    //Foto Empleado
    private UploadedFile uploadedFile2;
    private StreamedContent clientImage2 = null;

    //logueado
    private boolean loggedIn;

    /**
     * Creates a new instance of loginBean
     */
    public LoginBean() throws FileNotFoundException {

        logger.info("Constructor LoginBean");

        this.usuarioDao = new UsuarioDaoImplement();
        if (this.usuarioLog == null) {
            this.usuarioLog = new Usuario();
        }
        if (this.usuarioReg == null) {
            this.usuarioReg = new Usuario();
            this.usuarioReg.setUbicacion(new Ubicacion());
            this.usuarioReg.setFoto("Sin Foto");
        }
        if (this.empleadoReg == null) {
            this.empleadoReg = new Usuario();
            this.empleadoReg.setUbicacion(new Ubicacion());
            this.empleadoReg.setFoto("Sin Foto");
        }
    }

    @PostConstruct
    void init() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getContext();
        this.pathImages = servletContext.getRealPath(this.destination);
        logger.info("Se obtiene la ruta donde se van a despositar y leer imagenes {} ", this.pathImages);
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public UploadedFile getUploadedFile2() {
        return uploadedFile2;
    }

    public void setUploadedFile2(UploadedFile uploadedFile2) {
        this.uploadedFile2 = uploadedFile2;
    }

    public StreamedContent getClientImage() {
        if (uploadedFile != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
        } else {
            return this.clientImage;
        }
    }

    public void setClientImage(StreamedContent clientImage) {
        this.clientImage = clientImage;
    }

    public StreamedContent getClientImage2() {
        if (uploadedFile2 != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile2.getContents()), uploadedFile2.getContentType());
        } else {
            return this.clientImage2;
        }
    }

    public void setClientImage2(StreamedContent clientImage2) {
        this.clientImage2 = clientImage2;
    }

    public Usuario getUsuarioLog() {
        return usuarioLog;
    }

    public void setUsuarioLog(Usuario usuarioLog) {
        this.usuarioLog = usuarioLog;
    }

    public Usuario getUsuarioReg() {
        return usuarioReg;
    }

    public void setUsuarioReg(Usuario usuarioReg) {
        this.usuarioReg = usuarioReg;
    }

    public Usuario getEmpleadoReg() {
        return empleadoReg;
    }

    public void setEmpleadoReg(Usuario empleadoReg) {
        this.empleadoReg = empleadoReg;
    }

    public Usuario getUsuarioModificar() {
        return usuarioModificar;
    }

    public void setUsuarioModificar(Usuario usuarioModificar) {
        this.usuarioModificar = usuarioModificar;
    }

    public boolean isAceptaPoliticas() {
        return aceptaPoliticas;
    }

    public void setAceptaPoliticas(boolean aceptaPoliticas) {
        this.aceptaPoliticas = aceptaPoliticas;
    }

    public Usuario getUsuarioLogeado() {
        Usuario usr;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String mail = String.valueOf(session.getAttribute("email"));
        usr = this.usuarioDao.buscarUsuariobyEmail(mail);

        return usr;

    }

    public String login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        String ruta = "";
        String viewId = "";

        this.usuarioLog = usuarioDao.login(usuarioLog);
        if (usuarioLog != null && usuarioLog.getIdUsuario() != null) {
            this.loggedIn = true;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("email", usuarioLog.getEmail());
            for (Iterator iterator1 = usuarioLog.getRols().iterator(); iterator1.hasNext();) {
                Rol rol = (Rol) iterator1.next();
                session.setAttribute(rol.getNombre(), rol.getIdRol());
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", this.usuarioLog.getNombres());
            ruta = "/Front/index.xhtml";
            this.usuarioModificar = this.usuarioLog;
        } else {
            this.loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Usuario y/o Clave incorrecto.");
            if (this.usuarioLog == null) {
                this.usuarioLog = new Usuario();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
        //return ruta;
        //context.addCallbackParam("loggedIn", loggedIn);
        //context.addCallbackParam("ruta", ruta);

        //return "index?faces-redirect=true";
    }
    public String loginBody(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        String ruta = "";
        String viewId = "";

        this.usuarioLog = usuarioDao.login(usuarioLog);
        if (usuarioLog != null && usuarioLog.getIdUsuario() != null) {
            this.loggedIn = true;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("email", usuarioLog.getEmail());
            for (Iterator iterator1 = usuarioLog.getRols().iterator(); iterator1.hasNext();) {
                Rol rol = (Rol) iterator1.next();
                session.setAttribute(rol.getNombre(), rol.getIdRol());
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido ", this.usuarioLog.getNombres());
            ruta = "/Front/index.xhtml";
            this.usuarioModificar = this.usuarioLog;
        } else {
            this.loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Usuario y/o Clave incorrecto.");
            if (this.usuarioLog == null) {
                this.usuarioLog = new Usuario();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        //return viewId + "?faces-redirect=true";
        return ruta;
        //context.addCallbackParam("loggedIn", loggedIn);
        //context.addCallbackParam("ruta", ruta);

        //return "index?faces-redirect=true";
    }

    public boolean isLogged() {

        return this.loggedIn;

    }

    //Metodo para eliminar la session creada
    public String logout() {
        //Rura de redirecciÃ³n
        String ruta = "/Front/index.xhtml";
        //Instancia actual
        //RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        //Eliminar la sesion
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        //Enviar la variable ruta 
        //context.addCallbackParam("loggedOut", true);
        //context.addCallbackParam("ruta", ruta);
        return "index?faces-redirect=true";
    }

    public void registrarCliente() throws IOException {
        //Id del usuario a guardar
        int idGenerado = 0;
        /**
         * Si no hay foto Si hay foto se modifica adelante.
         */
        String sinFoto = "perfil-sinfoto.png";
        usuarioReg.setFoto(sinFoto);
        //Set fecha de creacion
        this.usuarioReg.setFechaCreacion(new Date());
        //busqueda de ubicacion seleccionada
        UbicacionDao ubdao = new UbicacionDaoImplement();
        Ubicacion ubicacionTemp = ubdao.buscarUbicacionById(this.usuarioReg.getUbicacion().getIdUbicacion());
        this.usuarioReg.setUbicacion(ubicacionTemp);
        this.usuarioReg.setEstado(Boolean.TRUE);

        //Setear Roles
        RolDao roldaoLink = new RolDaoImplement();
        Rol rolUsuario = roldaoLink.buscarRolByName("Usuario");
        Set<Rol> rols = new HashSet<Rol>();
        rols.add(rolUsuario);
        this.usuarioReg.setRols(rols);

        idGenerado = usuarioDao.insertarUsuario2(usuarioReg);
        idGenerado++;
        /**
         * Insertado correctamente.
         */
        if (idGenerado > 0) {
            /**
             * Si ingresa foto
             */
            if (uploadedFile != null) {
                //Actualizar nombre de foto en bd y subir foto
                String nombre = uploadedFile.getFileName();
                String extencion = nombre.substring(nombre.indexOf(".") + 1);
                String nombreFoto = "perfil-" + idGenerado + "." + extencion;
                usuarioReg.setFoto(nombreFoto);

                /**
                 * Subir archivo a server y modificar nombre en bd
                 */
                try {
                    TransferFile(nombreFoto, getUploadedFile().getInputstream());
                    usuarioDao.modificarUsuario(usuarioReg);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Bien!", "Se subio correctamente la foto"));
                } catch (IOException ex) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error", "Error subiendo la foto"));
                }

            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Completo", "Registrado exitosamente!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se registro el usuario correctamente!"));
        }

        ubicacionTemp = new Ubicacion();
        this.usuarioReg = new Usuario();
        this.usuarioReg.setUbicacion(new Ubicacion());
        this.usuarioReg.setFoto("perfil-sinfoto.png");
        this.uploadedFile = null;
        this.clientImage = null;
    }

    public void registrarEmpleado() {

        logger.info("Ingresar el metodo registrarEmpleado");

        //Id del usuario a guardar
        int idGenerado = 0;
        /**
         * Si no hay foto Si hay foto se modifica adelante.
         */
        String sinFoto = "perfil-sinfoto.png";
        empleadoReg.setFoto(sinFoto);

        try {

            this.empleadoReg.setIdUsuario(null);
            //Set fecha de creacion
            this.empleadoReg.setFechaCreacion(new Date());
            //busqueda de ubicacion seleccionada

            logger.info("Obtener la ubicacion ");
            UbicacionDao ubdao = new UbicacionDaoImplement();
            Ubicacion ubicacionTemp = ubdao.buscarUbicacionById(this.empleadoReg.getUbicacion().getIdUbicacion());
            this.empleadoReg.setUbicacion(ubicacionTemp);
            this.empleadoReg.setEstado(Boolean.TRUE);

            //Setear Roles
            logger.info("Cargar roles");
            RolDao roldaoLink = new RolDaoImplement();
            Rol rolUsuario = roldaoLink.buscarRolByName("Empleado");
            Set<Rol> rols = new HashSet<Rol>();
            rols.add(rolUsuario);
            this.empleadoReg.setRols(rols);

            logger.info("Buscar empleados para verificar si existe");
            empleadoReg = usuarioDao.buscarUsuario(empleadoReg);

            logger.info("Termino de buscar empleado ");
            logger.debug("Id de usuario [{}] ", empleadoReg.getIdUsuario());
            if (empleadoReg.getIdUsuario() == null || empleadoReg.getIdUsuario() <= 0) {
                logger.debug("Usuario no existe, se inicia la creacion ");
                idGenerado = usuarioDao.insertarUsuario2(empleadoReg);
                logger.debug("Usuario Creado  con id [{}]", idGenerado);
                /**
                 * Insertado correctamente.
                 */
                if (idGenerado > 0) {
                    /**
                     * Si ingresa foto
                     */

                    logger.debug("Se inicia carga de foto ");
                    if (uploadedFile2 != null) {
                        //Actualizar nombre de foto en bd y subir foto
                        String nombre = uploadedFile2.getFileName();
                        logger.debug("Nombre de la archivo [{}]", nombre);
                        String extencion = nombre.substring(nombre.indexOf(".") + 1);
                        String nombreFoto = "perfil-" + idGenerado + "." + extencion;
                        logger.debug("Nombre de la foto [{}]", nombreFoto);
                        empleadoReg.setFoto(nombreFoto);

                        /**
                         * Subir archivo a server y modificar nombre en bd
                         */
                        try {
                            logger.debug("Se hace transfer al archivo ");
                            TransferFile2(nombreFoto, getUploadedFile2().getInputstream());
                            logger.debug("Se va  a modificar el usuario ");
                            usuarioDao.modificarUsuario(empleadoReg);
                            logger.debug("el usuario se acaba de modificar. ");
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("Bien!", "Se subio correctamente la foto"));
                        } catch (Exception e) {
                            logger.error("Error Subiendo foto [{}]", e.getMessage());
                            throw new Exception("Error Cargando foto [" + e.getMessage() + "]");
                        }

                    } else {

                        logger.debug("No hay carga de foto");
                    }
                    ubicacionTemp = new Ubicacion();
                    this.uploadedFile2 = null;
                    this.empleadoReg = new Usuario();
                    this.empleadoReg.setUbicacion(new Ubicacion());
                    this.empleadoReg.setFoto("perfil-sinfoto.png");
                    this.clientImage2 = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Completo", "Registrado exitosamente!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se registro el usuario correctamente!"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "El usuario que intenta registrar ya existe"));
            }

        } catch (Exception ex) {
            logger.error("Error registrarEmpleado [{}] ", ex.getMessage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Se presento un error creando el usuario"));
        }

    }

    public String esAdmin() {
        for (Iterator iterator1 = usuarioLog.getRols().iterator(); iterator1.hasNext();) {
            Rol rol = (Rol) iterator1.next();
            if ("Administrador".equals(rol.getNombre())) {
                return "block";
            }
        }
        return "none";

    }

    public boolean isAdmin() {
        for (Iterator iterator1 = usuarioLog.getRols().iterator(); iterator1.hasNext();) {
            Rol rol = (Rol) iterator1.next();
            if ("Administrador".equals(rol.getNombre())) {
                return true;
            }
        }
        return false;

    }

    public Boolean esRol(String rol) {
        for (Iterator iterator1 = usuarioLog.getRols().iterator(); iterator1.hasNext();) {
            Rol rolU = (Rol) iterator1.next();
            if (rolU.getNombre().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public void modificarUsuario() {
        //busqueda de ubicacion seleccionada
        UbicacionDao ubdao = new UbicacionDaoImplement();
        Ubicacion ubicacionTemp = ubdao.buscarUbicacionById(this.usuarioModificar.getUbicacion().getIdUbicacion());
        this.usuarioModificar.setUbicacion(ubicacionTemp);
        this.usuarioModificar.setFechaActualizacion(new Date());

        UsuarioDao usuariodao = new UsuarioDaoImplement();
        String msg;
        if (usuariodao.modificarUsuario(this.usuarioModificar)) {
            this.usuarioLog = this.usuarioModificar;
            msg = "Se modifico correctamente el usuario";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("modificarPerfil.xhtml");
            } catch (IOException ex) {
                logger.error("Error [{}]", ex.getMessage());
            }

        } else {
            this.usuarioModificar = this.usuarioLog;
            msg = "Error al modificar el usuario";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadFoto(FileUploadEvent e) {
        //Asignar foto
        this.uploadedFile = e.getFile();
        this.clientImage = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
    }

    public void uploadFoto2(FileUploadEvent e) {
        //Asignar foto
        this.uploadedFile2 = e.getFile();
        this.clientImage2 = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile2.getContents()), uploadedFile2.getContentType());
    }

    public void TransferFile(String fileName, InputStream in) {

        try {
            OutputStream out = new FileOutputStream(new File(this.pathImages + fileName));
            int reader = 0;
            byte[] bytes = new byte[(int) getUploadedFile().getSize()];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void TransferFile2(String fileName, InputStream in) throws Exception {
        logger.info("Metodo trasnferFile2");
        try (OutputStream out = new FileOutputStream(new File(this.pathImages + fileName))) {

            int reader = 0;
            byte[] bytes = new byte[(int) getUploadedFile2().getSize()];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }

        } catch (IOException e) {
            logger.error("Error TransferFile [{}]", e);
            throw new Exception("Error TransferFile " + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void Autorizar() {
        if (!isLogged()) {
            try {
                /*ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/index.xhtml");*/
                FacesContext.getCurrentInstance().getExternalContext().redirect("Login.xhtml");
            } catch (Exception e) {

            }

        }
    }

    public void AutorizarAdmin() {
        if (!isAdmin()) {
            try {
                /*ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/index.xhtml");*/
                FacesContext.getCurrentInstance().getExternalContext().redirect("../Front/Login.xhtml");
            } catch (Exception e) {

            }

        }
    }
}
