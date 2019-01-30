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

    private final String employeeFile;
    private SmtpPostalOffice smtpPostalOffice;

    public BirthdayGreetings(String employeeFile, String host, int port) {
        this.employeeFile = employeeFile;
        smtpPostalOffice = new SmtpPostalOffice(host, port);
    }

    public void send(LocalDate today) throws MessagingException, IOException {
        // read from employee file
        List<String> allLines = redEmployeeFile();
        // parse csv
        for (String employeeLine : allLines) {
            List<String> employeePart = Arrays.stream(employeeLine.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            Employee employee = new Employee(
                    employeePart.get(0),
                    employeePart.get(1),
                    employeePart.get(3),
                    BirthDate.parse(employeePart.get(2)));

            // check birthday
            if(employee.isBirthday(today)) {
                smtpPostalOffice.sendMail(employee);
            }
        }
    }

    private List<String> redEmployeeFile() throws IOException {
        return Files.readAllLines(Paths.get(employeeFile)).stream()
                .skip(1)
                .collect(Collectors.toList());
    }
}
