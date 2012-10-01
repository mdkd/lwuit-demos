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
import com.nokia.example.birthdays.BirthdayMidlet.ExitListener;
import com.nokia.example.birthdays.data.Birthday;
import com.nokia.example.birthdays.data.BirthdayManager;
import com.nokia.example.birthdays.data.BirthdaySorter;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.list.DefaultListCellRenderer;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import java.util.Enumeration;
import java.util.Vector;

public class BirthdaysListView extends Form {

    private List birthdayList;
    private Command addCommand;
    private Command exitCommand;
    private BirthdayInsertionListener birthdayListener;

    public static interface BirthdayInsertionListener {
        public void birthdayInsertionRequested();
    }

    public BirthdaysListView(BirthdayInsertionListener birthdayInsertionListener, final ExitListener exitListener) {
        super("Birthdays");
        this.birthdayListener = birthdayInsertionListener;
        
        addCommands();
        createList();
    }
    
    private void createList() {
        if (birthdayList != null) {
            removeComponent(birthdayList);        
        }
        birthdayList = new List();
        addComponent(birthdayList);
        
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setShowNumbers(false);
        birthdayList.setRenderer(renderer);
                
        ListModel listModel = new DefaultListModel();
        birthdayList.setModel(listModel);
        
        populateBirthdays();
    }
    
    private void addCommands() {
        addCommand = new Command("Add") {
            public void actionPerformed(ActionEvent e) {
                birthdayListener.birthdayInsertionRequested();
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
    }
    
    public void refresh() {
        createList();
    }

    public final void populateBirthdays() {
        Vector birthdays = BirthdayManager.getInstance().getBirthdays();
        BirthdaySorter.sort(birthdays);
        
        Enumeration birthdayElements = birthdays.elements();
        while (birthdayElements.hasMoreElements()) {
            Birthday bd = (Birthday) birthdayElements.nextElement();
            birthdayList.addItem(bd);
        }

        if (birthdayList.getModel().getSize() == 0) {
            birthdayList.addItem("No saved birthdays.");
        }        
    }
}
