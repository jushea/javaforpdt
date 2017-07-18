package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by USER on 18.07.2017.
 */
public class ContactDeletionTests extends TestBase{
    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().returnToHomePage();
        app.getContactHelper().selectGroup();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().returnToHomePage();
    }
}
