package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by USER on 18.07.2017.
 */
public class ContactHelper extends HelperBase {
    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }
    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getSurname());
        type(By.name("address"), contactData.getStreet());
        type(By.name("home"), contactData.getPhoneHome());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("work"), contactData.getPhoneWork());
        type(By.name("email"), contactData.getEmail());
    }
}
