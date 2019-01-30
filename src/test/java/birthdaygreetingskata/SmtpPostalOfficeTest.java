package birthdaygreetingskata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.LocalSmtpServer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SmtpPostalOfficeTest {

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
    void sendMailWorks() throws MessagingException {
        SmtpPostalOffice smtpPostalOffice = new SmtpPostalOffice(SERVER, PORT);
        smtpPostalOffice.sendMail(new Employee("Duque", "Johan", "j.duque@acme.com", BirthDate.parse("1987-01-04")));

        final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();

        LocalSmtpServer.MailReceived expectedMsg = getMailReceived("j.duque@acme.com", "Johan");
        assertTrue(messages.contains(expectedMsg));
    }

    private LocalSmtpServer.MailReceived getMailReceived(String to, String firstName) {
        return new LocalSmtpServer.MailReceived("greeter@acme.com", to, "Happy birthday!", "Happy birthday, dear " + firstName + "!");
    }


}
