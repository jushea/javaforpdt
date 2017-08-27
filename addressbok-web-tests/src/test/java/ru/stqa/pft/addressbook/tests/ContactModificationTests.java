package ru.stqa.pft.addressbook.tests;

import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
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
        if(app.db().contacts().size() == 0) {
            app.goTo().contactCreation();
            app.contact().create(new ContactData().withFirstname("name" + RandomUtils.nextInt())
                    .withSurname("surname" + RandomUtils.nextInt())
                    .withStreet("Street 123 build 3")
                    .withMobile("8(3812)123-456")
                    .withPhoneHome("8-913-123-45-67")
                    .withPhoneWork("8(3812)789-012")
                    .withEmail("email_for_this_man@gmail.com")
                    .withEmail2("email2@mail.ru")
                    .withEmail3("email3@mail.ru")
                    .withPhoto(new File("src/test/resources/work.png")));
        }
        if(app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group1").withHeader("test2").withFooter("test3"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifyContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifyContact.getId())
                .withFirstname("name" + RandomUtils.nextInt())
                .withSurname("surname" + RandomUtils.nextInt())
                .withStreet("Street 123 build 3")
                .withMobile("8(3812)123-456")
                .withPhoneWork("8-913-123-45-67")
                .withPhoneHome("8(3812)789-012")
                .withEmail("email_for_this_man@gmail.com")
                .withEmail2("email2@mail.ru")
                .withEmail3("email3@mail.ru")
                .withPhoto(new File("src/test/resources/work.png"));
        app.contact().modify(contact);
        assertEquals(app.contact().getContactCount(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifyContact).withAdded(contact)));
    }

    @Test
    public void testAddContactToGroup() {
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData modifyContact = null;
        for(ContactData c : before) {
            if(c.getGroups().size() < groups.size()) {
                modifyContact = c;
                break;
            }
        }

        GroupData modifyGroup = null;
        if(modifyContact != null) {
            for(GroupData g : groups) {
                boolean key = false;
                for(GroupData gm : modifyContact.getGroups()) {
                    if(g.equals(gm)) {
                        key = true;
                        break;
                    }
                }
                if(!key) {
                    modifyGroup = g;
                    break;
                }
            }
        } else {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("name" + RandomUtils.nextInt()).withHeader("test2").withFooter("test3"));
            app.goTo().homePage();
            modifyContact = before.iterator().next();
            groups = app.db().groups();

            int i = groups.stream().mapToInt((c) -> c.getId()).max().getAsInt();
            modifyGroup.withId(i);
        }

        app.contact().toGroup(modifyContact, modifyGroup);
        Groups groupsAfter = app.db().groups();

        for(GroupData a: groupsAfter) {
            if(a.getId() == modifyGroup.getId()) {
                assertThat(a.getContacts().size(), equalTo(modifyGroup.getContacts().size() + 1));
            }
        }
    }

    @Test
    public void testDeleteContactToGroup() {
        Contacts contacts = app.db().contacts();//контакты
        Groups groups = app.db().groups();//группы

        ContactData modifyContact = null;//контакт
        for(ContactData c : contacts) {
            if(c.getGroups().size() > 0) {
                modifyContact = c;
                break;
            }
        }//выбрать контакт с группой

        Contacts before = null;
        GroupData modifyGroup = null;
        if(modifyContact != null) {//если есть контакт берем группу
            modifyGroup = modifyContact.getGroups().iterator().next();
            before = modifyGroup.getContacts();
        } else {//если нет то берем контакт и группу и связываем
            modifyContact = contacts.iterator().next();
            modifyGroup = groups.iterator().next();
            app.contact().toGroup(modifyContact, modifyGroup); //получить список групп из БД и посчитать у группы колво контактов

            before = app.db().groups().iterator().next().getContacts();
        }

        app.contact().fromGroup(modifyContact, modifyGroup);

        Contacts after = app.db().groups().iterator().next().getContacts();

        assertThat(after.size(), equalTo(before.size() - 1));
    }
}
