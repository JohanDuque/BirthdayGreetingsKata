package birthdaygreetingskata;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(surname, employee.surname) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(birthDate, employee.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, email, birthDate);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
