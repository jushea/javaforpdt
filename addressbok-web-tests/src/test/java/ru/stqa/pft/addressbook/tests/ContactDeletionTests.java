package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by USER on 18.07.2017.
 */
public class ContactDeletionTests extends TestBase{
    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().returnToHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name", "surname", "Street 123 build 3",
                    "8(3812)123-456", "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com"));
        }
        app.getContactHelper().selectGroup();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().returnToHomePage();
    }
}
