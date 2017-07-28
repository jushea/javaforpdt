package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private final String firstname;
    private final String surname;
    private final String street;
    private final String phoneHome;
    private final String mobile;
    private final String phoneWork;
    private final String email;

    public ContactData(int id, String firstname, String surname, String street, String phoneHome, String mobile, String phoneWork, String email) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.street = street;
        this.phoneHome = phoneHome;
        this.mobile = mobile;
        this.phoneWork = phoneWork;
        this.email = email;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return surname != null ? surname.equals(that.surname) : that.surname == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
