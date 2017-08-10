package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

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
        Contacts before = app.contact().all();
        ContactData modifyContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifyContact.getId())
                .withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneWork("8-913-123-45-67")
                .withPhoneHome("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com");
        app.contact().modify(contact);

        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size());

        assertThat(after, equalTo(before.without(modifyContact).withAdded(contact)));
    }


}
