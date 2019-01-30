package birthdaygreetingskata;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetings {

    private SmtpPostalOffice smtpPostalOffice;
    private CsvEmployeeFileReader csvEmployeeFileReader;

    BirthdayGreetings(String employeeFile, String host, int port) {
        this.csvEmployeeFileReader = new CsvEmployeeFileReader(employeeFile);
        smtpPostalOffice = new SmtpPostalOffice(host, port);
    }

    void send(LocalDate today) throws MessagingException, IOException {
        List<Employee> employees = csvEmployeeFileReader.getAllEmployees();

        for (Employee employee : employees) {
            if (employee.isBirthday(today)) {
                smtpPostalOffice.sendMail(employee);
            }
        }
    }
}
