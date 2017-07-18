package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by USER on 18.07.2017.
 */
public class ContactCreationTest extends TestBase {
    @Test
    public void testContactCreation() {
        app.getNavigationHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("name", "surname", "Street 123 build 3", "8(3812)123-456", "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().returnToHomePage();
    }
}
