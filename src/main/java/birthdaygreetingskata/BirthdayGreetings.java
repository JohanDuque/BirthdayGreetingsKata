package birthdaygreetingskata;

import javax.mail.MessagingException;
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

    BirthdayGreetings(String employeeFile, String host, int port) {
        this.employeeFile = employeeFile;
        smtpPostalOffice = new SmtpPostalOffice(host, port);
    }

    void send(LocalDate today) throws MessagingException, IOException {
        List<Employee> employees = parseEmployees(readEmployeeFile());

        for (Employee employee : employees) {
            if (employee.isBirthday(today)) {
                smtpPostalOffice.sendMail(employee);
            }
        }
    }

    private List<Employee> parseEmployees(List<String> allLines) {
        return allLines.stream().map(this::parseEmployee).collect(Collectors.toList());
    }

    private Employee parseEmployee(String employeeLine) {
        List<String> employeePart = Arrays.stream(employeeLine.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return new Employee(
                employeePart.get(0),
                employeePart.get(1),
                employeePart.get(3),
                BirthDate.parse(employeePart.get(2)));
    }

    private List<String> readEmployeeFile() throws IOException {
        return Files.readAllLines(Paths.get(employeeFile)).stream()
                .skip(1)
                .collect(Collectors.toList());
    }
}
