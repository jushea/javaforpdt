package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by USER on 18.07.2017.
 */
public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getNavigationHelper().returnToHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name", "surname", "Street 123 build 3",
                    "8(3812)123-456", "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com"));
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("name123456", "surname111", "Street 123 build 3",
                "8(3812)123-789", "8-913-123-45-99", "8(3812)789-012", "email_for_this_man032@gmail.com"));
        app.getContactHelper().updateContact();
        app.getNavigationHelper().returnToHomePage();
    }
}
