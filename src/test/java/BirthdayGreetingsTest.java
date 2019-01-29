import birthdaygreetingskata.BirthdayGreetings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.LocalSmtpServer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {

    private LocalSmtpServer smtpServer;
    private static final String MAIL_HOG_BIN = "./src/test/java/support/MailHog_darwin_amd64";
    private final String filename = "employees_test.txt";

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
    void prepareFile() throws IOException {
        Files.write(Paths.get("employees.txt"), Arrays.asList(
                "last_name, first_name, date_of_birth, email",
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        List<String> lines = Files.readAllLines(Paths.get("employees.txt"));
        lines.forEach(System.out::println);
    }

    @BeforeEach
    void setUp() {
        smtpServer = new LocalSmtpServer(MAIL_HOG_BIN, "127.0.0.1", 1027, 8027, 8027);
        smtpServer.start();
    }

    @AfterEach
    void tearDown() {
        smtpServer.stop();
    }

    @Test
    void sendMail() {
        smtpServer.sendMail("test@acme", "who@knows.com", "This is my subject", "this is the body of my message");
        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();
        assertEquals(1, messages.size());
    }

    @Test
    void oneBirthday() throws IOException, MessagingException {
        Files.write(Paths.get(filename), Arrays.asList(
                "last_name, first_name, date_of_birth, email",
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        BirthdayGreetings greetings = new BirthdayGreetings(filename);
        greetings.send(LocalDate.parse("2018-10-08"));

        //Read form employees file
        //Check for birthday
        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();
        assertEquals(1, messages.size());

        final LocalSmtpServer.MailReceived msg = messages.get(0);
        assertEquals("al.capone@acme.com", msg.getTo());
        assertEquals("greeter@acme.com", msg.getFrom());
        assertEquals("Happy birthday!", msg.getSubject());
        assertEquals("Happy birthday, dear Al!", msg.getBody());

        //read mail sent to smtp server
        //assert on mail received

        //Delete employee file
    }
}
