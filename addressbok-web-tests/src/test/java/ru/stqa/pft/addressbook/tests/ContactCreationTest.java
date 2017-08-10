package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTest extends TestBase {
    @Test(enabled = false)
    public void testContactCreation() {
        Set<ContactData> before = app.contact().all();
        app.goTo().contactCreation();
        ContactData contact = new ContactData().withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneHome("8-913-123-45-67")
                .withPhoneWork("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com");
        app.contact().create(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(before.size() + 1, after.size());

        contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
