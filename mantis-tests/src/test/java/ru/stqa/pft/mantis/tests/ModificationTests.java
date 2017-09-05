package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertTrue;

public class ModificationTests extends TestBase {

    @Test
    public void testModification() throws IOException, InterruptedException, MessagingException {
        //precondition
        long now = System.currentTimeMillis();
        String login = String.format("jamesuser%s", now);
        String password = "password";
        String email = String.format("jamesuser%s@localhost", now);
        //test
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        app.modification().login(adminLogin, adminPassword);
        app.modification().manageUsers();

        app.modification().createUser(login, email);

        Users users = app.db().users();
        Optional<UserData> firstUser = users.stream().filter((u) -> u.getName().contains(login)).findFirst();
        int id = firstUser.get().getId();

        if(!app.james().doesUserExist(login)) {
            app.james().createUser(login, password);
        }

        app.modification().resetPassword(id);

        List<MailMessage> mailMessages = app.james().waitForMail(login, password, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, email);

        app.registration().finish(confirmationLink, password);

        assertTrue(app.newSession().login(login, password));
        //postcondition
        app.james().deleteUser(login, password);
    }

}
