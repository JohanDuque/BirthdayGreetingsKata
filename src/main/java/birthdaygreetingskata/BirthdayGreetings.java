package birthdaygreetingskata;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetings {

    private SmtpPostalOffice smtpPostalOffice;
    private CsvEmployeeFileRegistry csvEmployeeFileRegistry;

    public BirthdayGreetings(CsvEmployeeFileRegistry csvEmployeeFileRegistry, SmtpPostalOffice smtpPostalOffice) {
        this.csvEmployeeFileRegistry = csvEmployeeFileRegistry;
        this.smtpPostalOffice = smtpPostalOffice;
    }

    void send(LocalDate today) throws MessagingException, IOException {
        List<Employee> employees = csvEmployeeFileRegistry.getAllEmployees();

        for (Employee employee : employees) {
            if (employee.isBirthday(today)) {
                smtpPostalOffice.sendMail(employee);
            }
        }
    }
}
