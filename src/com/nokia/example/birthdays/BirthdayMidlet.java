package com.nokia.example.birthdays;

import com.nokia.example.birthdays.data.BirthdayManager;
import java.util.Date;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.nokia.example.birthdays.view.BirthdaysListView;
import com.nokia.example.birthdays.view.ChooseBirthdayView;
import com.nokia.example.birthdays.view.BirthdaysListView.BirthdayInsertionListener;
import com.nokia.example.birthdays.view.ChooseBirthdayView.BirthdayListener;
import com.sun.lwuit.Display;

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
        createViews();
        
        birthDaysListView.show();
    }

    private void createViews() {
        birthDaysListView = new BirthdaysListView(new BirthdayInsertionListener() {
                public void birthdayInsertionRequested() {
                    chooseBirthdayView.show();
                }
            }, new ExitListener() {
                public void exitCommanded() {
                    notifyDestroyed();
                }
            }
        );

        chooseBirthdayView = new ChooseBirthdayView(new BirthdayListener() {
                public void birthdayAdded(String name, Date birthday) {
                    BirthdayManager.getInstance().addBirthday(name, birthday);
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
