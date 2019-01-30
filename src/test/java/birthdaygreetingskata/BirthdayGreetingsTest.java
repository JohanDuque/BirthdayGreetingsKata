package birthdaygreetingskata;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BirthdayGreetingsTest {

    private static final String SERVER = "127.0.0.1";
    private static final int PORT = 1027;
    private LocalSmtpServer smtpServer;
    private static final String MAIL_HOG_BIN = "./src/test/java/support/MailHog_darwin_amd64";
    private final String filename = "employees_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        smtpServer = new LocalSmtpServer(MAIL_HOG_BIN, SERVER, PORT, 8027, 8027);
        smtpServer.start();

        Files.deleteIfExists(Paths.get(filename));
    }

    @AfterEach
    void tearDown() {
        smtpServer.stop();
    }

    @Test
    void oneBirthday() throws IOException, MessagingException {
        prepareFile(filename, Arrays.asList(
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        BirthdayGreetings greetings = new BirthdayGreetings(filename, SERVER, PORT);
        greetings.send(LocalDate.parse("2018-10-08"));

        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();
        assertEquals(1, messages.size());

        final LocalSmtpServer.MailReceived msg = messages.get(0);
        assertEquals("al.capone@acme.com", msg.getTo());
        assertEquals("greeter@acme.com", msg.getFrom());
        assertEquals("Happy birthday!", msg.getSubject());
        assertEquals("Happy birthday, dear Al!", msg.getBody());
    }

    @Test
    void noBirthday() throws IOException, MessagingException {
        prepareFile(filename, Arrays.asList(
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        BirthdayGreetings greetings = new BirthdayGreetings(filename, SERVER, PORT);
        greetings.send(LocalDate.parse("2018-12-31"));

        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();
        assertEquals(0, messages.size());
    }

    @Test
    void manyBirthdays() throws IOException, MessagingException {
        prepareFile(filename, Arrays.asList(
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        BirthdayGreetings greetings = new BirthdayGreetings(filename, SERVER, PORT);
        greetings.send(LocalDate.parse("1987-09-11"));

        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();

        LocalSmtpServer.MailReceived expectedMsg = getMailReceived("pablo.escobar@acme.com", "Pablo");
        assertTrue(messages.contains(expectedMsg));

        expectedMsg = getMailReceived("john.wick@acme.com", "John");
        assertTrue(messages.contains(expectedMsg));
        assertEquals(2, messages.size());

    }

    private LocalSmtpServer.MailReceived getMailReceived(String to, String firstName) {
        return new LocalSmtpServer.MailReceived("greeter@acme.com", to, "Happy birthday!", "Happy birthday, dear " + firstName + "!");
    }

    private void prepareFile(String filename, List<String> lines) throws IOException {
        final String header = "last_name, first_name, date_of_birth, email";

        List<String> fileLines = new ArrayList<>();
        fileLines.add(header);
        fileLines.addAll(lines);

        Files.write(Paths.get(filename), fileLines);
    }
}
