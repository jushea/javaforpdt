package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ModificationHelper extends HelperBase {

    public ModificationHelper(ApplicationManager app) {
        super(app);
    }


    public void login(String username, String password) {
        type(By.id("username"), username);
        click(By.cssSelector("input[type='submit']"));
        type(By.id("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public void manageUsers() {
//        click(By.cssSelector("#menu-toggler"));
        click(By.xpath("//a[@href='/mantisbt-2.5.1/manage_overview_page.php']"));
        click(By.xpath("//a[@href='/mantisbt-2.5.1/manage_user_page.php']"));
    }

    public void resetPassword(int id) {
        click(By.xpath("//a[@href='manage_user_edit_page.php?user_id=" + id + "']"));
        click(By.xpath(".//*[@id='manage-user-reset-form']/fieldset/span/input"));
    }

    public void createUser(String user, String email) throws InterruptedException {
        click(By.xpath(".//*[@id='manage-user-div']/div[1]/form/fieldset/input[2]"));
        type(By.id("user-username"), user);
        type(By.id("email-field"), email);
        click(By.xpath(".//*[@id='manage-user-create-form']/div/div[3]/input"));
        Thread.sleep(3000);
        click(By.xpath("//a[@href='/mantisbt-2.5.1/manage_user_page.php']"));
    }
}
