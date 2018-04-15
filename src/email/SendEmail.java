package email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail {

    public static String sendFromGmail(String to){
        ResourceBundle rb = ResourceBundle.getBundle("resources.DBProp");

        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useLower(true).useUpper(true).build();
        String password = passwordGenerator.generate(16);


        String subject = "Your randomly generated password";
        String body = "Your password is " + password + ". Use this alongside your email to rent bikes" +
                "\n(note: please do not reply to this email)." ;

        try {

            String from = rb.getString("gmail.username");
            String pass = rb.getString("gmail.password");

            Properties props = System.getProperties();
            String host = "smtp.gmail.com";
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pass);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            try {
                message.setFrom(new InternetAddress(from));
                InternetAddress toAdress = new InternetAddress(to);

                message.addRecipient(Message.RecipientType.TO, toAdress);
                message.setSubject(subject);
                message.setText(body);
                Transport transport = session.getTransport("smtp");
                transport.connect(host, from, pass);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                return password;

            } catch (AddressException e) {
                System.out.println(e.getMessage() + " - sendFromGmail()");
            }
        }  catch (MessagingException e) {
            System.out.println(e.getMessage() + " - sendFromGmail()");
        }
        return null;
    }

    public static void main(String[] args){
        sendFromGmail("msandn3s@gmail.com");
    }
}
