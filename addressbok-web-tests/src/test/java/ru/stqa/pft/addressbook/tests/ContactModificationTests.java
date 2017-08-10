package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if(app.contact().list().size() == 0) {
            app.goTo().contactCreation();
            app.contact().create(new ContactData().withFirstname("name" + RandomUtils.nextInt())
                    .withSurname("surname" + RandomUtils.nextInt())
                    .withStreet("Street 123 build 3")
                    .withMobile("8(3812)123-456")
                    .withPhoneHome("8-913-123-45-67")
                    .withPhoneWork("8(3812)789-012")
                    .withEmail("email_for_this_man@gmail.com"));
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> before = app.contact().list();
        int item = before.size();
        ContactData contact = new ContactData().withId(before.get(item-1).getId())
                .withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneWork("8-913-123-45-67")
                .withPhoneHome("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com");
        app.contact().modify(item, contact);

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(before.size(), after.size());

        before.remove(item-1);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}
