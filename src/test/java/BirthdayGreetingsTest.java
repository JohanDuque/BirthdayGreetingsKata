import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {

    @Test
    void itWorks() {
        assertEquals("a", "a");
    }

    //This is an end to end test
    @Test
    void justAReference() {
        //Start smtp server
        //Prepare employee file

        //BirthdayGreetings greetings = new BirthdayGreetings();
        //greetings.send();
        //Read form employees file
        //Check for birthday
        //Send mail through mail client

        //read mail sent to smtp server
        //assert on mail received

        //Delete employee file
        //Start smtp server
    }

    @Test
    void sendMail() throws MessagingException {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "1025");
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("test@acme"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress("who@knows.com"));
        msg.setSubject("Greetings");
        msg.setText("Hello banana!");

        // Send the message
        Transport.send(msg);
    }
}
