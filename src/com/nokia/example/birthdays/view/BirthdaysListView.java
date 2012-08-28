package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.BirthdayMidlet;
import com.nokia.example.birthdays.BirthdayMidlet.ExitListener;
import com.nokia.example.birthdays.data.Birthday;
import com.nokia.example.birthdays.data.BirthdayManager;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.list.DefaultListModel;
import java.util.Enumeration;

public class BirthdaysListView extends Form {

    private List birthdayList;
    private Command addCommand;
    private Command exitCommand;

    public static interface BirthdayInsertionListener {
        public void birthdayInsertionRequested();
    }

    public BirthdaysListView(final BirthdayInsertionListener birthdayInsertionListener, final ExitListener exitListener) {
        super("Birthdays");
        
        birthdayList = new List();
        addComponent(birthdayList);
        populateBirthdays();

        addCommand = new Command("Add") {
            public void actionPerformed(ActionEvent e) {
                birthdayInsertionListener.birthdayInsertionRequested();
            }
        };
        addCommand(addCommand);

        exitCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
                BirthdayMidlet.getInstance().notifyDestroyed();
            }
        };
        addCommand(exitCommand);
        setBackCommand(exitCommand);
        
        List birthdayList = new List();
        addComponent(birthdayList);
    }

    public void show() {
        super.show();
        populateBirthdays();
    }

    public void populateBirthdays() {
        birthdayList.setModel(new DefaultListModel());
        Enumeration birthdays = BirthdayManager.getInstance().getBirthdays().elements();
        while (birthdays.hasMoreElements()) {
            Birthday bd = (Birthday) birthdays.nextElement();
            birthdayList.addItem(bd);
        }

        if (birthdayList.getModel().getSize() == 0) {
            birthdayList.addItem("No saved birthdays.");
        }
    }
}
