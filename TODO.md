##TEST LIST

- SmtpPostalOffice
    - smtp response KO

- missing file
- empty file

- Convert oneBirthday Test to use message receiver
- Smtp postalOffice doesn't care about all Employee info (only mail and first name)


### FILE FORMAT

last_name, first_name, date_of_birth, email
Capone, Al, 1951-10-08, al.capone@acme.com
Escobar, Pablo, 1975-09-11, pablo.escobar@acme.com
Wick, John, 1987-09-11, john.wick@acme.com


### DONE

- one Birthday
    - Al, Pablo, John
    - John's birthday => mail to John

- many birthdays
    - Al, Pablo, John
    - Al & Pablo  => mail to Al and Pablo

- no bithday
    - Al, Pablo, John
    - no birthdays => no mails
    
- smtp mail sent
- smtp unreachable
