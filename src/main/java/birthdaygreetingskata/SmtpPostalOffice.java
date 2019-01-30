package birthdaygreetingskata;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpPostalOffice implements PostalOffice {

    private final String host;
    private final Integer port;

    public SmtpPostalOffice(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void dispatchMessage(Employee employee) throws MessagingException {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port.toString());
        Session session = Session.getInstance(props, null);

        // Format the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("greeter@acme.com"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(employee.getEmail()));
        msg.setSubject("Happy birthday!");
        msg.setText("Happy birthday, dear " + employee.getName() + "!");

        // Send the message
        Transport.send(msg);
    }
}
