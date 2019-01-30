package birthdaygreetingskata;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BirthdayGreetings {

    private PostalOffice postalOffice;
    private EmployeeRegistry employeeRegistry;

    public BirthdayGreetings(EmployeeRegistry employeeRegistry, PostalOffice postalOffice) {
        this.employeeRegistry = employeeRegistry;
        this.postalOffice = postalOffice;
    }

    void send(LocalDate today) throws MessagingException, IOException {
        List<Employee> employees = employeeRegistry.getAllEmployees();

        for (Employee employee : employees) {
            if (employee.isBirthday(today)) {
                postalOffice.dispatchMessage(employee);
            }
        }
    }
}
