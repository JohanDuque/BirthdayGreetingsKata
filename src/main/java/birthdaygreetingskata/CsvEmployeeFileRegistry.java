package birthdaygreetingskata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvEmployeeFileRegistry implements EmployeeRegistry {
    String employeeFile;

    public CsvEmployeeFileRegistry(String employeeFile) {
        this.employeeFile = employeeFile;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        return parseEmployees(readEmployeeFile());
    }

    List<Employee> parseEmployees(List<String> allLines) {
        return allLines.stream().map(this::parseEmployee).collect(Collectors.toList());
    }

    Employee parseEmployee(String employeeLine) {
        List<String> employeePart = Arrays.stream(employeeLine.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return new Employee(
                employeePart.get(0),
                employeePart.get(1),
                employeePart.get(3),
                BirthDate.parse(employeePart.get(2)));
    }

    List<String> readEmployeeFile() throws IOException {
        return Files.readAllLines(Paths.get(employeeFile)).stream()
                .skip(1)
                .collect(Collectors.toList());
    }
}
