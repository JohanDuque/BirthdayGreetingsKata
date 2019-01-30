import birthdaygreetingskata.BirthDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BirthDateTest {

    @Test
    void isBirthday() {
        BirthDate birthDate = BirthDate.parse("1987-09-16");

        assertTrue(birthDate.isBirthday(LocalDate.parse("2018-09-16")));
    }

    @Test
    void isNotBirthday() {
        BirthDate birthDate = BirthDate.parse("1987-09-16");

        assertFalse(birthDate.isBirthday(LocalDate.parse("2018-09-17")));
        assertFalse(birthDate.isBirthday(LocalDate.parse("2018-10-16")));
    }
}
