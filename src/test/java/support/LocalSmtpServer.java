package support;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.turbomanage.httpclient.BasicHttpClient;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;

public class LocalSmtpServer {
    private Process mailHog;
    private final Integer smtpPort;
    private final Integer apiPort;
    private final Integer uiPort;
    private final String smtpHost;
    private final String mailHogBin;

    public LocalSmtpServer(String mailHogBin) {
        this.mailHogBin = mailHogBin;
        smtpHost = "127.0.0.1";
        smtpPort = 1027;
        apiPort = 1082;
        uiPort = 8082;
    }

    public LocalSmtpServer(String mailHogBin, String smtpHost, Integer smtpPort, Integer apiPort, Integer uiPort) {
        this.mailHogBin = mailHogBin;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.apiPort = apiPort;
        this.uiPort = uiPort;
    }

    public void start() {
        StringBuilder args = new StringBuilder();
        args.append(mailHogBin + " ");
        args.append(String.format("-smtp-bind-addr :%s ", smtpPort));
        args.append(String.format("-api-bind-addr :%s ", apiPort));
        args.append(String.format("-ui-bind-addr :%s", uiPort));

        try {
            mailHog = Runtime.getRuntime().exec(args.toString());
        } catch (IOException e) {
            throw new LocalSmtpException("Cannot start process: " + args.toString(), e);
        }

        while (!isSocketAlive(smtpHost, smtpPort)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        mailHog.destroy();
    }

    public ServerState currentState() {
        String response = httpClient().get("v2/messages", null).getBodyAsString();
        return parseJson(response);
    }

    public void clearState() {
        httpClient().delete("v1/messages", null);
    }

    public void sendMail(String from, String to, String subject, String body) {
        // Create a mail session
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", smtpHost.toString());
        props.put("mail.smtp.port", smtpPort.toString());
        Session session = Session.getInstance(props, null);

        // Construct the message
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(body);

            // Send the message
            Transport.send(msg);
        } catch (MessagingException ex) {
            throw new LocalSmtpException("Cannot send message", ex);
        }
    }

    private BasicHttpClient httpClient() {
        String apiUrl = String.format("http://%s:%s/api/", smtpHost, apiPort);
        return new BasicHttpClient(apiUrl);
    }

    private ServerState parseJson(String result) {
        Any rawServerState = JsonIterator.deserialize(result);
        int count = rawServerState.get("count").toInt();
        ArrayList<MailReceived> messages = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Any msg = rawServerState.get("items", i);
            String from = msg.get("From", "Mailbox") + "@" + msg.get("From", "Domain");
            String to = msg.get("To", 0, "Mailbox") + "@" + msg.get("To", 0, "Domain");
            String subject = msg.get("Content", "Headers", "Subject", 0).toString();
            String body = msg.get("Content", "Body").toString();
            messages.add(new MailReceived(from, to, subject, body));
        }

        return new ServerState(count, messages);
    }

    public static boolean isSocketAlive(String hostName, int port) {
        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(hostName, port);
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isAlive = true;

        } catch (SocketTimeoutException exception) {
            System.out.println("SocketTimeoutException " + hostName + ":" + port + ". " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println(
                    "IOException - Unable to connect to " + hostName + ":" + port + ". " + exception.getMessage());
        }
        return isAlive;
    }

    public static class MailReceived {
        private final String from;
        private final String to;
        private final String subject;
        private final String body;

        public MailReceived(String from, String to, String subject, String body) {

            this.from = from;
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MailReceived that = (MailReceived) o;
            return Objects.equals(from, that.from) &&
                    Objects.equals(to, that.to) &&
                    Objects.equals(subject, that.subject) &&
                    Objects.equals(body, that.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, subject, body);
        }

        @Override
        public String toString() {
            return "MailReceived{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", subject='" + subject + '\'' +
                    ", body='" + body + '\'' +
                    '}';
        }
    }

    public static class ServerState {
        private final int receivedMessages;
        private final ArrayList<MailReceived> messages;

        public ServerState(int receivedMessages, ArrayList<MailReceived> messages) {
            this.receivedMessages = receivedMessages;
            this.messages = messages;
        }

        public int getReceivedMessages() {
            return receivedMessages;
        }

        public ArrayList<MailReceived> getMessages() {
            return messages;
        }
    }

    private class LocalSmtpException extends RuntimeException {
        public LocalSmtpException(String message, Throwable e) {
            super(message, e);
        }
    }
}
