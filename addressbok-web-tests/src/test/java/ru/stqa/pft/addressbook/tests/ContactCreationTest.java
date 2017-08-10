package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {
    @Test(enabled = false)
    public void testContactCreation() {
        List<ContactData> before = app.contact().list();
        app.goTo().contactCreation();
        ContactData contact = new ContactData().withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneHome("8-913-123-45-67")
                .withPhoneWork("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com");
        app.contact().create(contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(before.size() + 1, after.size());

        /*contact.setId(after.stream().max((c1, c2) -> Integer.compare(c1.getId(), c2.getId())).get().getId());
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);*/
        Assert.assertEquals(before, after);
    }
}
