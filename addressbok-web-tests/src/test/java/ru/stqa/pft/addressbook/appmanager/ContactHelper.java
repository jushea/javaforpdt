package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        type(By.name("email"), contactData.getEmail1());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        attach(By.name("photo"), contactData.getPhoto());

        if(isElementPresent(By.name("new_group"))) {
            if(contactData.getGroups().size() > 0) {
                new Select(wd.findElement(By.name("new_group")))
                        .selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        }
    }

    public boolean isElementPresent(By by) {
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        if(wd.findElements(by).size() == 0) {
            return false;
        } else return true;
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
        contactCache = null;
        navigation.homePage();
    }

    public void modify(ContactData contact) {
        editContactById(contact.getId());
        /*initContactModification();*/
        create(contact);
    }

    private void editContactById(int id) {
        wd.findElement(By.cssSelector(".center>a[href='edit.php?id=" + id + "']>img")).click();
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

    Contacts contactCache = null;
    public Contacts all() {
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath(".//*[@id='maintable']/tbody/tr"));
        String lname, fname, allPhones;
        String allEmail;
        int id;
        for(int i = 1; i < elements.size(); i++) {
            lname = elements.get(i).findElement(By.xpath("td[2]")).getText();
            fname = elements.get(i).findElement(By.xpath("td[3]")).getText();
            id = Integer.parseInt(elements.get(i).findElement(By.xpath("td[1]/input")).getAttribute("id"));
            allPhones = elements.get(i).findElement(By.xpath("td[6]")).getText();
            allEmail = elements.get(i).findElement(By.xpath("td[5]")).getText();
            contactCache.add(new ContactData().withId(id).withFirstname(fname).withSurname(lname).
                    withAllPhones(allPhones).withAllEmail(allEmail));
        }
        return new Contacts(contactCache);
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        closeAlert();
        contactCache = null;
        navigation.homePage();
    }

    private void selectContactById(int id) {
        wd.findElement(By.xpath(".//*[@id='" + id + "']")).click();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withSurname(lastname)
                .withPhoneHome(home).withMobile(mobile).withPhoneWork(work)
                .withEmail(email1).withEmail2(email2).withEmail3(email3);
    }

    private void initContactModificationById(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void toGroup(ContactData contact, GroupData group) {
        wd.findElement(By.id(String.valueOf(contact.getId()))).click();//поиск контакта
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());//выбрать группу
        wd.findElement(By.name("add")).click();//нажать кнопку
        navigation.homePage();
    }

    public void fromGroup(ContactData contact, GroupData group) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
        wd.findElement(By.id(String.valueOf(contact.getId()))).click();
        wd.findElement(By.name("remove")).click();
        navigation.homePage();
    }
}
