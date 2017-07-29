/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.UsuarioDao;
import dao.UsuarioDaoImplement;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Ubicacion;
import model.Usuario;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author anfevari
 */
@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private Usuario usuario;
    private Usuario createUsuario;
    private Integer usuariosCount;
    private Usuario usuarioRecuperar;

    //Foto
    private String destination = "/var/webapp/images/";
    //Foto Cliente
    private UploadedFile uploadedFile;
    private StreamedContent clientImage = null;

    public UsuarioBean() {
        this.usuarios = new ArrayList<Usuario>();
        if (usuario == null) {
            usuario = new Usuario();
            usuario.setUbicacion(new Ubicacion());
        }
        if (createUsuario == null) {
            createUsuario = new Usuario();
            createUsuario.setUbicacion(new Ubicacion());
        }

        Date creationDate = new Date(Calendar.getInstance().getTime().getTime());
        Date modificacionDate = new Date(Calendar.getInstance().getTime().getTime());
        usuario.setFechaCreacion(creationDate);
        usuarioRecuperar = new Usuario();
    }

    public List<Usuario> getUsuarios() {
        UsuarioDao usuariodao = new UsuarioDaoImplement();
        usuarios = usuariodao.mostrarUsuarios();
        return usuarios;

    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
   

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getCreateUsuario() {
        return createUsuario;
    }

    public void setCreateUsuario(Usuario createUsuario) {
        this.createUsuario = createUsuario;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
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

    public Usuario getUsuarioRecuperar() {
        return usuarioRecuperar;
    }

    public void setUsuarioRecuperar(Usuario usuarioRecuperar) {
        this.usuarioRecuperar = usuarioRecuperar;
    }

    public void btnCreateUsuario(ActionEvent actionevent) {
        String msg;
        //Id del usuario a guardar
        int idGenerado = 0;
        /**
         * Si no hay foto Si hay foto se modifica adelante.
         */

        String sinFoto = "/images/perfil-sinfoto.png";
        createUsuario.setFoto(sinFoto);
        //Actualizar fecha
        createUsuario.setFechaCreacion(new java.util.Date());
        UsuarioDao usuariodao = new UsuarioDaoImplement();
        idGenerado = usuariodao.insertarUsuario2(this.createUsuario);
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
                createUsuario.setFoto(nombreFoto);

                /**
                 * Subir archivo a server y modificar nombre en bd
                 */
                try {
                    TransferFile(nombreFoto, getUploadedFile().getInputstream());
                    usuariodao.modificarUsuario(createUsuario);
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
        this.createUsuario = new Usuario();
        createUsuario.setUbicacion(new Ubicacion());        
        this.createUsuario.setFoto("/images/perfil-sinfoto.png");
        this.uploadedFile = null;
        this.clientImage = null;

    }

    public void btnUpdateUsuario(ActionEvent actionEvent) {
        UsuarioDao usuariodao = new UsuarioDaoImplement();
        String msg;
        if (usuariodao.modificarUsuario(this.usuario)) {
            msg = "Se modifico correctamente el usuario";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            msg = "Error al modificar el usuario";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Integer getUsuariosCount() {
        UsuarioDao linkDao = new UsuarioDaoImplement();
        usuariosCount = linkDao.countAll();
        return usuariosCount;

    }

    public void recuperarUsuario() throws NoSuchAlgorithmException {

        //verificar email y documento
        //Si exite devolver usuario
        //Si no enviar mensaje de error
        UsuarioDao usuariodao = new UsuarioDaoImplement();
        usuarioRecuperar = usuariodao.buscarUsuariobyEmailnCedula(usuarioRecuperar);
        if (usuarioRecuperar == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verificación fallida!",
                    "No se encontro coincidencia entre el email y el documento de identidad"));
        } else {
            //Generar contraseña
            String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "y", "z", "A", "E", "I", "O", "U", "Z"};
            int length = 10;
            Random random = SecureRandom.getInstanceStrong();    // as of JDK 8, this should return the strongest algorithm available to the JVM
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int indexRandom = random.nextInt(symbols.length);
                sb.append(symbols[indexRandom]);
            }
            String passwordGenerada = sb.toString();

            //Modificar usuario 
            usuarioRecuperar.setContrasena(passwordGenerada);
            usuariodao.modificarUsuario(usuarioRecuperar);

            //enviar email con contraseña
            //Ejemplo tomado de http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
            //Correo y contraseña del servidor
            final String username = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("MAILFROM");
            final String password = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("MAILPASS");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(usuarioRecuperar.getEmail()));
                message.setSubject("Recuperar Contraseña");
                message.setText("Contraseña nueva: " + passwordGenerada);

                Transport.send(message);

                System.out.println("Done");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bien!", "La contraseña se ha enviado a su correo electronico."));

            } catch (MessagingException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "!", "El mensaje no se pudo enviar. Intente de nuevo más tarde"));
                throw new RuntimeException(e);
            }

        }

        usuarioRecuperar = new Usuario();

    }

    public void uploadFoto(FileUploadEvent e) {
        //Asignar foto
        this.uploadedFile = e.getFile();
        this.clientImage = new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents()), uploadedFile.getContentType());
    }

    public void TransferFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(destination + fileName));
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
     public Usuario UsuarioFromUsr(Usuario usuario) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        
        return udi.buscarUsuario(usuario);
    }
     
     public Usuario UsuarioByID(Usuario usuario) {
        UsuarioDaoImplement udi = new UsuarioDaoImplement();
        
        return udi.buscarUsuariobyID(usuario.getIdUsuario().toString());
    }

}
