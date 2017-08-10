package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {
    NavigationHelper navigation = new NavigationHelper(wd);

    public ContactHelper(WebDriver wd) {
        super(wd);
    }
    public void submitContactCreation() {
        click(By.xpath(".//input[@type='submit']"));
    }

    public void initContactModification(int item) {
        item++;
        click(By.xpath("//table[@id='maintable']/tbody/tr[" + item + "]/td[8]/a/img"));
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

    public void deleteSelectedContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void create(ContactData contact) {
        fillContactForm(contact);
        submitContactCreation();
        navigation.homePage();
    }

    public void modify(ContactData contact) {
        selectGroupById(contact.getId());
        /*initContactModification();*/
        create(contact);
    }
    public void delete(int index) {
        selectGroup(index);
        deleteSelectedContact();
        closeAlert();
        navigation.homePage();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.xpath(".//*[@id='maintable']/tbody/tr"));
        String lname, fname;
        int id;
        for(int i = 1; i < elements.size(); i++) {
            lname = elements.get(i).findElement(By.xpath("td[2]")).getText();
            fname = elements.get(i).findElement(By.xpath("td[3]")).getText();
            id = Integer.parseInt(elements.get(i).findElement(By.xpath("td[1]/input")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstname(fname).withSurname(lname));
        }
        return contacts;
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> elements = wd.findElements(By.xpath(".//*[@id='maintable']/tbody/tr"));
        String lname, fname;
        int id;
        for(int i = 1; i < elements.size(); i++) {
            lname = elements.get(i).findElement(By.xpath("td[2]")).getText();
            fname = elements.get(i).findElement(By.xpath("td[3]")).getText();
            id = Integer.parseInt(elements.get(i).findElement(By.xpath("td[1]/input")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstname(fname).withSurname(lname));
        }
        return contacts;
    }

    public void delete(ContactData contact) {
        selectGroupById(contact.getId());
        deleteSelectedContact();
        closeAlert();
        navigation.homePage();
    }


}
