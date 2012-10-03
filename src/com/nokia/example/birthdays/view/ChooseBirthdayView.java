/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *
 * See LICENSE.TXT for license information.
 */
package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.BirthdayMidlet;
import com.nokia.example.birthdays.BirthdayMidlet.BackListener;
import com.nokia.example.birthdays.data.Birthday;
import com.sun.lwuit.Calendar;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import java.util.Date;

public class ChooseBirthdayView extends Form {

    private Calendar calendar;
    private TextField nameField;
    private Command saveCommand;
    private Command backCommand;
    
    private Label nameLabel;
    private Label dateLabel;

    public static interface BirthdayListener {
        public void birthdayAdded(Birthday birthday);
    }

    public ChooseBirthdayView(final BirthdayListener birthdayListener,
        final BackListener backListener) {
        
        super("Add birthday");

        createComponents();
        initializeCommands(birthdayListener, backListener);
    }
    
    private void createComponents() {
        nameLabel = new Label("Name");
        nameField = new TextField();
        nameField.setLabelForComponent(nameLabel);
        
        dateLabel = new Label("Date of birth");
        calendar = new Calendar();
        calendar.setLabelForComponent(dateLabel);
        addComponent(nameLabel);
        addComponent(nameField);
        addComponent(dateLabel);
        addComponent(calendar);
        
        clearFields();
    }
    
    private void initializeCommands(final BirthdayListener birthdayListener,
        final BackListener backListener) {
        
        saveCommand = new Command("Save") {
            public void actionPerformed(ActionEvent e) {
                validateAndSave(birthdayListener);
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
    
    private void validateAndSave(final BirthdayListener birthdayListener) {
        Date selectedDate = calendar.getDate();
        if ("".equals(nameField.getText())) {
            BirthdayMidlet.getInstance().showErrorDialog(
                "Name empty", "Please enter a name.");
            return;
        }
        else if (selectedDate.getTime() > new Date().getTime()) {
            BirthdayMidlet.getInstance().showErrorDialog(
                "Invalid date", "Birthday must be in the past.");
            return;
        }
        
        // Set the event to happen at around 10am
        selectedDate = adjustTimeOfDay(selectedDate);
        
        birthdayListener.birthdayAdded(
            new Birthday(nameField.getText(), calendar.getDate()));
        clearFields();
    }
    
    private Date adjustTimeOfDay(Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        c.set(java.util.Calendar.HOUR_OF_DAY, 10);
        
        return c.getTime();        
    }
    
    private void clearFields() {
        nameField.clear();
        calendar.setDate(new Date(0));
    }
}
