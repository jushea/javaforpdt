package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase{
    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().returnToHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().initContactCreation();
            app.getContactHelper().createContact(new ContactData("name", "surname", "Street 123 build 3",
                    "8(3812)123-456", "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectGroup(0);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size()-1, after.size());

        before.remove(0);
        Assert.assertEquals(before, after);
    }
}
