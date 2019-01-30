package birthdaygreetingskata;

import java.time.LocalDate;

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
}
