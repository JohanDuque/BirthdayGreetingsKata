package birthdaygreetingskata;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetings {

    private PostalOffice postalOffice;
    private CsvEmployeeFileRegistry csvEmployeeFileRegistry;

    public BirthdayGreetings(CsvEmployeeFileRegistry csvEmployeeFileRegistry, PostalOffice postalOffice) {
        this.csvEmployeeFileRegistry = csvEmployeeFileRegistry;
        this.postalOffice = postalOffice;
    }

    void send(LocalDate today) throws MessagingException, IOException {
        List<Employee> employees = csvEmployeeFileRegistry.getAllEmployees();

        for (Employee employee : employees) {
            if (employee.isBirthday(today)) {
                postalOffice.dispatchMessage(employee);
            }
        }
    }
}
