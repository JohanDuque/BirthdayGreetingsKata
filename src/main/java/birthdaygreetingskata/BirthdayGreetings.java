package birthdaygreetingskata;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BirthdayGreetings {

    private static final String GREETER_SENDER = "greeter@acme.com";

    public void send(LocalDate today) throws MessagingException, IOException {

        List<String> lines = Files.readAllLines(Paths.get("employees.txt")).stream().skip(1).collect(Collectors.toList());
        final String line = lines.get(0);
        final List<String> employeeInfo = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());

        //"last_name, first_name, date_of_birth, email",
        final String receiverMail = employeeInfo.get(3);
        final String firstName = employeeInfo.get(1);

        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "127.0.0.1");
        props.put("mail.smtp.port", "1027");
        Session session = Session.getInstance(props, null);

        // Construct the message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(GREETER_SENDER));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverMail));
        msg.setSubject("Happy birthday!");
        msg.setText("Happy birthday, dear "+firstName+"!");

        // Send the message
        Transport.send(msg);
    }
}
