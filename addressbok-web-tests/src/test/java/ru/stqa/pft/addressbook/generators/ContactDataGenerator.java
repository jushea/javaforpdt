package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.appmanager.DbHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Contact count")
    public int count;
    @Parameter(names = "-f", description = "Target file")
    public String file;
    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = JCommander.newBuilder().addObject(generator).build();
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if(format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if(format.equals("xml")) {
            saveAsXml(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        try(Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        try(Writer writer = new FileWriter(file)) {
            for(ContactData contact: contacts) {
                writer.write(String.format("%s:%s:%s:%s:%s:%s:%s:%s:%s\n", contact.getFirstname(), contact.getSurname(),
                        contact.getStreet(), contact.getPhoneHome(), contact.getMobile(), contact.getPhoneWork(),
                        contact.getEmail1(), contact.getEmail2(), contact.getEmail3()));
            }
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for(int i = 0; i < count; i++) {
            contacts.add(new ContactData()
                    .withFirstname(String.format("First %s", i))
                    .withSurname(String.format("Last %s", i))
                    .withStreet(String.format("Street %s", i))
                    .withPhoneHome(String.format("Homephone %s", i))
                    .withMobile(String.format("MobilePhone %s", i))
                    .withPhoneWork(String.format("WorkPhone %s", i))
                    .withEmail(String.format("Email %s", i))
                    .withEmail2(String.format("Email2 %s", i))
                    .withEmail3(String.format("Email3 %s", i))
                    .withPhoto(new File("src/test/resources/work.png"))
                    .inGroup(new GroupData().withName("group1").withHeader("header").withFooter("footer")));

        }
        return contacts;
    }
}
