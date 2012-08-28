package com.nokia.example.birthdays.view;

import java.util.Date;

import com.nokia.example.birthdays.BirthdayMidlet.BackListener;
import com.sun.lwuit.Calendar;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;

public class ChooseBirthdayView extends Form {

    private Calendar calendar;
    private TextField nameField;
    private Command saveCommand;
    private Command backCommand;

    public static interface BirthdayListener {
        public void birthdayAdded(String name, Date birthday);
    }

    public ChooseBirthdayView(final BirthdayListener birthdayListener, final BackListener backListener) {
        super("Add birthday");

        calendar = new Calendar();
        addComponent(calendar);

        nameField = new TextField();
        nameField.setLabelForComponent(new Label("Name"));
        addComponent(nameField);

        saveCommand = new Command("Save") {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                Date selectedDate = calendar.getDate();
                birthdayListener.birthdayAdded(name, selectedDate);
            }
        };
        addCommand(saveCommand);
        setDefaultCommand(saveCommand);

        backCommand = new Command("Back") {
            public void actionPerformed(ActionEvent e) {
                backListener.backCommanded();
            }
        };
        addCommand(backCommand);
        setBackCommand(backCommand);
    }
}
