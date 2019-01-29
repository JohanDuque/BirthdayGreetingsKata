package birthdaygreetingskata;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;

public class BirthdayGreetings {

    public void send(LocalDate today) throws MessagingException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "1027");
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("greeter@acme.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("al.capone@acme.com"));
        msg.setSubject("Happy birthday!");
        msg.setText("Happy birthday, dear Al!");

        // Send the message
        Transport.send(msg);
    }
}
