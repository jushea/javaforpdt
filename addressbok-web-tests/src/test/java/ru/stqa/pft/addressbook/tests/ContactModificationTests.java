package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test(enabled = false)
    public void testContactModification() {
        app.goTo().returnToHomePage();
        if(!app.getContactHelper().isThereAContact()) {
            app.goTo().initContactCreation();
            app.getContactHelper().createContact(new ContactData("name", "surname", "Street 123 build 3",
                    "8(3812)123-456", "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com"));
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        int item = 1;
        app.getContactHelper().initContactModification(item);

        ContactData contact = new ContactData(before.get(item-1).getId(), "name" + RandomUtils.nextInt(), "surname" + RandomUtils.nextInt(), "Street 123 build 3", "8(3812)123-456",
                "8-913-123-45-67", "8(3812)789-012", "email_for_this_man@gmail.com");
        app.getContactHelper().createContact(contact);

        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());

        before.remove(item-1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
