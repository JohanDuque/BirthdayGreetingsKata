import org.junit.jupiter.api.Test;
import support.LocalSmtpServer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {

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
    void sendMail() {
        String mailHogBin = "/Users/duque/intr3/Kata/src/test/java/support/MailHog_darwin_amd64";
        LocalSmtpServer smtpServer = new LocalSmtpServer(mailHogBin, "127.0.0.1", 1027, 8027, 8027);

        smtpServer.start();

        try {
            smtpServer.sendMail("test@acme", "who@knows.com", "This is my subject", "this is the body of my message");
            final ArrayList<LocalSmtpServer.MailReceived> messages = smtpServer.currentState().getMessages();
            assertEquals(1, messages.size());

        } finally {
            smtpServer.stop();
        }

    }
}
