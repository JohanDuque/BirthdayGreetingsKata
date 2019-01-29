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
    private final String filename;
    private final String server;
    private final int port;

    public BirthdayGreetings(String filename, String server, int port) {
        this.filename = filename;
        this.server = server;
        this.port = port;
    }

    public void send(LocalDate today) throws MessagingException, IOException {

        List<String> lines = Files.readAllLines(Paths.get(filename)).stream().skip(1).collect(Collectors.toList());
        final String line = lines.get(0);
        final List<String> employeeInfo = Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList());

        //"last_name, first_name, date_of_birth, email",
        final String receiverMail = employeeInfo.get(3);
        final String firstName = employeeInfo.get(1);
        LocalDate birthDate = LocalDate.parse(employeeInfo.get(2));

        //Check birthday date
        if(birthDate.getDayOfMonth() == today.getDayOfMonth() && birthDate.getMonth() == today.getMonth()){
            // Create a mail session
            java.util.Properties props = new java.util.Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", String.valueOf(port));
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
}
