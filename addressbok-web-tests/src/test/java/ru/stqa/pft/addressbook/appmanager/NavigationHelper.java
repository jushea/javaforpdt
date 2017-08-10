package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by USER on 18.07.2017.
 */
public class NavigationHelper extends HelperBase {
    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        click(By.linkText("groups"));
    }

    public void homePage() {
        click(By.linkText("home"));
    }

    public void contactCreation() {
        click(By.linkText("add new"));
    }
}
