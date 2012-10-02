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
import com.nokia.example.birthdays.view.BirthdaysListView;
import com.nokia.example.birthdays.view.BirthdaysListView.BirthdayInsertionListener;
import com.nokia.example.birthdays.view.ChooseBirthdayView;
import com.nokia.example.birthdays.view.ChooseBirthdayView.BirthdayListener;
import com.sun.lwuit.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

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
        checkPIMAvailability();        
        createViews();
        
        birthDaysListView.show();
    }
    
    private void checkPIMAvailability() {
        if (System.getProperty("microedition.pim.version") == null) {
            // TODO: handle with a proper dialog
            System.out.println("PIM API is required but not found");
        }        
    }

    /*
     * Create application vies. The Midlet acts as a central controller,
     * changing views with the help of listeners.
     */
    private void createViews() {
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

        chooseBirthdayView = new ChooseBirthdayView(
            new BirthdayListener() {
                public void birthdayAdded(Birthday birthday) {
                    BirthdayListModel.getInstance().addItem(birthday);
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
