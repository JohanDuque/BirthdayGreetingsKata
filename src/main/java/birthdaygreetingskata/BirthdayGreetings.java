package birthdaygreetingskata;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class BirthdayGreetings {

    public void send() throws MessagingException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "1027");
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("greeter@acme.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("to@acme.com"));
        msg.setSubject("This is my subject");
        msg.setText("This is my body");

        // Send the message
        Transport.send(msg);
    }
}
