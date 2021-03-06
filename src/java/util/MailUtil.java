/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Jotamarios
 */
public class MailUtil {

    public void enviarMail(String toMail, String asunto, String texto) {
        final String username = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("MAILFROM");
        final String password = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("MAILPASS");
        final String mailport = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("MAILPORT");
        final String smtphost = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SMTPHOST");
        final String smtpauth = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SMTPAUTH");
        final String smtpssl = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("SMTP_SSL");

        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpauth);
        props.put("mail.smtp.starttls.enable", smtpssl);
        props.put("mail.smtp.host", smtphost);
        props.put("mail.smtp.port", mailport);

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
                    InternetAddress.parse(toMail));
            message.setSubject(asunto);
            message.setText(texto);

            Transport.send(message);

        } catch (MessagingException e) {

            throw new RuntimeException(e);
        }
    }
}
