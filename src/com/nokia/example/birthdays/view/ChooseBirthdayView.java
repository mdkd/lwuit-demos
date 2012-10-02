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
        calendar = new Calendar();
        addComponent(calendar);

        nameField = new TextField();
        nameField.setLabelForComponent(new Label("Name"));
        addComponent(nameField);
        
        clearFields();
    }
    
    private void initializeCommands(final BirthdayListener birthdayListener,
        final BackListener backListener) {
        
        saveCommand = new Command("Save") {
            public void actionPerformed(ActionEvent e) {
                birthdayListener.birthdayAdded(
                    new Birthday(nameField.getText(), calendar.getDate()));
                clearFields();
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
    
    private void clearFields() {
        nameField.clear();
        calendar.setCurrentDate(new Date(0));
    }
}
