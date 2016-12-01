/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author andre
 */
@Named(value = "contactoBean")
@ViewScoped
public class ContactoBean implements Serializable {

    private String nombres;
    private String apellidos;
    private String email;
    private String asunto;
    private String mensaje;

    /**
     * Creates a new instance of ContactoBean
     */
    public ContactoBean() {
        nombres = "";
        apellidos = "";
        email = "";
        asunto = "";
        mensaje = "";
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void enviarMensaje() {
        //Ejemplo tomado de http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
        //Correo y contraseña del servidor
        final String username = "correo@gmail.com";
        final String password = "contraseña";

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
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(username));
            message.setSubject(asunto);
            message.setText(mensaje
                    + "\nMensaje Enviador por: \n"
                    + "Email: " + email + ".\n"
                    + "Nombres: " + nombres + ".\n"
                    + "Apellidos: " + apellidos + ".");

            Transport.send(message);

            System.out.println("Done");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bien!", "Mensaje enviado correctamente."));

        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "!", "El mensaje no se pudo enviar. Intente de nuevo más tarde"));
            throw new RuntimeException(e);
        }
        nombres = "";
        apellidos = "";
        email = "";
        asunto = "";
        mensaje = "";
    }

}
