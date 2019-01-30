package birthdaygreetingskata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*TEST LIST
 * - many employees
 * - missing file
 * - no employee
 * - file malformed
 * */

class CsvEmployeeFileRegistryTest {

    private final String filename = "employees_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(filename));
    }

    @Test
    void manyEmployees() throws IOException {
        prepareFile(filename, Arrays.asList(
                "Capone, Al, 1951-10-08, al.capone@acme.com",
                "Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com",
                "Wick, John, 1987-09-11, john.wick@acme.com"
        ));

        CsvEmployeeFileRegistry csvEmployeeFileRegistry = new CsvEmployeeFileRegistry(filename);
        final List<Employee> allEmployees = csvEmployeeFileRegistry.getAllEmployees();

        assertEquals(3, allEmployees.size());
        assertTrue(allEmployees.contains(new Employee("Capone", "Al", "al.capone@acme.com", BirthDate.parse("1951-10-08"))));
        assertTrue(allEmployees.contains(new Employee("Escobar", "Pablo", "pablo.escobar@acme.com", BirthDate.parse("1975-09-11"))));
    }

    @Test
    void missingFile() {
        String missingFilename = "ThisFIleDoesNotExist";
        CsvEmployeeFileRegistry csvEmployeeFileRegistry = new CsvEmployeeFileRegistry(missingFilename);

        assertThrows(NoSuchFileException.class, () ->  csvEmployeeFileRegistry.getAllEmployees());
    }

    private void prepareFile(String filename, List<String> lines) throws IOException {
        final String header = "last_name, first_name, date_of_birth, email";

        List<String> fileLines = new ArrayList<>();
        fileLines.add(header);
        fileLines.addAll(lines);

        Files.write(Paths.get(filename), fileLines);
    }

}
