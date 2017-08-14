package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {
    @Test
    public void testContactCreation() {
        Contacts before = app.contact().all();
        app.goTo().contactCreation();
        ContactData contact = new ContactData().withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneHome("8-913-123-45-67")
                .withPhoneWork("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com");
        app.contact().create(contact);
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()+1));

        //contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}
