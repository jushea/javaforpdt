package ru.stqa.pft.addressbook;

public class ContactData {
    private final String firstname;
    private final String surname;
    private final String street;
    private final String phoneHome;
    private final String mobile;
    private final String phoneWork;
    private final String email;

    public ContactData(String firstname, String surname, String street, String phoneHome, String mobile, String phoneWork, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.street = street;
        this.phoneHome = phoneHome;
        this.mobile = mobile;
        this.phoneWork = phoneWork;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getStreet() {
        return street;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public String getEmail() {
        return email;
    }
}
