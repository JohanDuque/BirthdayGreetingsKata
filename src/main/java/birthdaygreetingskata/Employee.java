package birthdaygreetingskata;

import java.time.LocalDate;

public class Employee {
    private final String surname;
    private final String name;
    private final String email;
    private BirthDate birthDate;

    public Employee(String surname, String name, String email, BirthDate birthDate) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BirthDate getBirthDate() {
        return birthDate;
    }

    boolean isBirthday(LocalDate today) {
        return this.birthDate.isBirthday(today);
    }
}
