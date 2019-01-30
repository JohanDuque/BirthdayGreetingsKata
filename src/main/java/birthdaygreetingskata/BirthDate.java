package birthdaygreetingskata;

import java.time.LocalDate;
import java.util.Objects;

public class BirthDate {

    private LocalDate birthDate;

    public BirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static BirthDate parse(String value) {
        return new BirthDate(LocalDate.parse(value));
    }

    public boolean isBirthday(LocalDate today) {
        return this.birthDate.getDayOfMonth() == today.getDayOfMonth() && this.birthDate.getMonth() == today.getMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthDate birthDate1 = (BirthDate) o;
        return Objects.equals(birthDate, birthDate1.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate);
    }

    @Override
    public String toString() {
        return "BirthDate{" +
                "birthDate=" + birthDate +
                '}';
    }
}
