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
import com.nokia.example.birthdays.data.PIMNotAccessibleException;
import com.nokia.example.birthdays.util.Compatibility;
import com.nokia.example.birthdays.view.BirthdayCreateView;
import com.nokia.example.birthdays.view.BirthdayCreateView.BirthdayListener;
import com.nokia.example.birthdays.view.BirthdayListModel;
import com.nokia.example.birthdays.view.BirthdayListView;
import com.nokia.example.birthdays.view.BirthdayListView.BirthdayInsertionListener;
import com.nokia.example.birthdays.view.ContactListView;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;

public class BirthdayMidlet extends MIDlet {

    private static BirthdayMidlet instance;
    private BirthdayListView birthDaysListView;
    private ContactListView contactListView;
    private BirthdayCreateView birthdayCreateView;
    
    private ContactList pimContactList;

    public static interface BackListener {
        public void backCommanded();
    }

    public static interface ExitListener {
        public void exitCommanded();
    }

    public static BirthdayMidlet getInstance() {
        return instance;
    }
    
    public ContactList getPIMContactList() {
        return pimContactList;
    }

    public BirthdayMidlet() {
    }

    protected void startApp() throws MIDletStateChangeException {
        instance = this;

        Display.init(this);        
        new Form().show();
        
        boolean pimAccessible = openPIMConnection();        
        if (!pimAccessible) {
            System.out.println("PIM not accessible");
            shutDownOnPIMError();
            return;            
        }
        
        createViews();
        birthDaysListView.show();
    }
    
    private boolean openPIMConnection() {
        if (System.getProperty("microedition.pim.version") == null) {
            return false;
        }
            
        try {
            pimContactList = (ContactList)
                PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.READ_WRITE);
            return true;
        }
        catch (PIMException ex) {
            System.out.println("Exception: " + ex.getMessage());
            return false;
        }
    }
    
    private boolean openPIMContactList() {
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
        birthDaysListView = new BirthdayListView(
            new BirthdayInsertionListener() {
                public void birthdayInsertionRequested(Contact contact) {
                    contactListView.show();
                }
            }, new ExitListener() {
                public void exitCommanded() {
                    notifyDestroyed();
                }
            }
        );

        final BirthdayListModel listModel = BirthdayListModel.getInstance();
        contactListView = new ContactListView(new BirthdayInsertionListener() {
                public void birthdayInsertionRequested(final Contact contact) {                    
                    
                    birthdayCreateView = new BirthdayCreateView(
                        contact,
                        new BirthdayListener() {
                            public void birthdayAdded(Birthday birthday) {
                                try {
                                    listModel.addItem(birthday);
                                    birthDaysListView.show();
                                    contactListView.refresh();
                                } catch (PIMNotAccessibleException ex) {
                                    showErrorDialog("Sorry", ex.getMessage());
                                }
                            }
                        }, new BackListener() {
                            public void backCommanded() {
                                birthDaysListView.show();
                            }
                        }
                    );
                    birthdayCreateView.show();
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

    protected void destroyApp(boolean unconditional)
        throws MIDletStateChangeException {
        
        if (pimContactList != null) {
            try {
                pimContactList.close();
            }
            catch (PIMException ex) {}
        }
    }
}
