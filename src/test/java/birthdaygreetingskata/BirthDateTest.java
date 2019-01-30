package birthdaygreetingskata;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void equality() {
        BirthDate birthDate = BirthDate.parse("1987-09-16");
        BirthDate birthDateCopy = BirthDate.parse("1987-09-16");
        BirthDate birthDateDifferent = BirthDate.parse("1986-09-16");

        BirthDate birthDateDiffMonth = BirthDate.parse("1987-01-16");
        BirthDate birthDateDiffDay = BirthDate.parse("1987-09-11");
        BirthDate birthDateDiffYear = BirthDate.parse("1999-09-16");

        assertEquals(birthDate, birthDateCopy);
        assertNotEquals(birthDate, birthDateDifferent);
        assertNotEquals(birthDate, birthDateDiffDay);
        assertNotEquals(birthDate, birthDateDiffMonth);
        assertNotEquals(birthDate, birthDateDiffYear);
    }
}
