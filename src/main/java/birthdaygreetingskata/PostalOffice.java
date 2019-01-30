package birthdaygreetingskata;

import javax.mail.MessagingException;

public interface PostalOffice {
    void dispatchMessage(Employee employee) throws MessagingException;
}
