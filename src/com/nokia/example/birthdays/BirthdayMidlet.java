/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *
 * See LICENSE.TXT for license information.
 */
package com.nokia.example.birthdays;

import com.nokia.example.birthdays.data.Birthday;
import com.nokia.example.birthdays.data.BirthdayListModel;
import com.nokia.example.birthdays.data.PIMContactHandler;
import com.nokia.example.birthdays.data.PIMContactHandler.PIMNotAccessibleException;
import com.nokia.example.birthdays.util.Compatibility;
import com.nokia.example.birthdays.view.BirthdaysListView;
import com.nokia.example.birthdays.view.BirthdaysListView.BirthdayInsertionListener;
import com.nokia.example.birthdays.view.ChooseBirthdayView;
import com.nokia.example.birthdays.view.ChooseBirthdayView.BirthdayListener;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import java.util.Vector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;

public class BirthdayMidlet extends MIDlet {

    private static BirthdayMidlet instance;
    private BirthdaysListView birthDaysListView;
    private ChooseBirthdayView chooseBirthdayView;

    public static interface BackListener {
        public void backCommanded();
    }

    public static interface ExitListener {
        public void exitCommanded();
    }

    public static BirthdayMidlet getInstance() {
        return instance;
    }

    public BirthdayMidlet() {
    }

    protected void startApp() throws MIDletStateChangeException {
        instance = this;

        Display.init(this);        
        new Form().show();
        
        boolean pimAccessible = checkPIMAvailability();
        try {            
            createViews();
        } catch (PIMNotAccessibleException ex) {
            pimAccessible = false;
        }    
        if (!pimAccessible) {
            System.out.println("PIM not accessible");
            shutDownOnPIMError();
            return;            
        }
        
        birthDaysListView.show();
    }
    
    private boolean checkPIMAvailability() {
        if (System.getProperty("microedition.pim.version") == null) {
            return false;
        }
        
        ContactList contactList;
        try {
            contactList = (ContactList)
                PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
            contactList.close();
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public void shutDownOnPIMError() {
        System.out.println("shutDownOnPIMError()");
        try {
            showErrorDialog("PIM API required",
                "The PIM API is required but could not be accessed.");
            destroyApp(true);
            notifyDestroyed();
        }
        catch (MIDletStateChangeException ex) {
            System.out.println("MIDlet state not changed: " + ex.getMessage());
        }
    }

    public void showErrorDialog(String title, String message) {
        Dialog.show(title, message, Compatibility.toLowerCaseIfFT("Ok"), null);
    }
    
    /*
     * Create application vies. The Midlet acts as a central controller,
     * changing views with the help of listeners.
     */
    private void createViews() throws PIMNotAccessibleException {
        birthDaysListView = new BirthdaysListView(
            new BirthdayInsertionListener() {
                public void birthdayInsertionRequested() {
                    chooseBirthdayView.show();
                }
            }, new ExitListener() {
                public void exitCommanded() {
                    notifyDestroyed();
                }
            }
        );

        final BirthdayListModel listModel = BirthdayListModel.getInstance();
        chooseBirthdayView = new ChooseBirthdayView(
            new BirthdayListener() {
                public void birthdayAdded(Birthday birthday) {
                    listModel.addItem(birthday);
                    birthDaysListView.show();
                }
            }, new BackListener() {
                public void backCommanded() {
                    birthDaysListView.show();
                }
            }
        );
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
