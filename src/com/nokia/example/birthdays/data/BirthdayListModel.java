/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *
 * See LICENSE.TXT for license information.
 */
package com.nokia.example.birthdays.data;

import com.nokia.example.birthdays.BirthdayMidlet;
import com.nokia.example.birthdays.data.PIMContactHandler.PIMNotAccessibleException;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import java.util.Vector;

/**
 * A representation of the Birthday list handled by the application,
 * sorted in the order of next upcoming birthdays.
 */
public class BirthdayListModel
    extends DefaultListModel
    implements ListModel {
    
    private static BirthdayListModel instance;
    
    private BirthdaySorter sorter = new BirthdaySorter();
    private PIMContactHandler pimHandler;
    
    public static BirthdayListModel getInstance() throws PIMNotAccessibleException {
        if (instance == null) {
            instance = new BirthdayListModel();
        }
        return instance;
    }
    
    private Vector birthdays;
    
    private BirthdayListModel() throws PIMNotAccessibleException {
        pimHandler = PIMContactHandler.getInstance();
        birthdays = pimHandler.getBirthdays();
        
        System.out.println(
            "BirthdayListModel created with " + birthdays.size() + " items");
        
        sorter.sort(birthdays);
    }
    
    public void addItem(Object o) {
        Birthday birthday = (Birthday) o;
        System.out.println("BirthdayListModel received a new Birthday: " + birthday);
        pimHandler.addBirthday(birthday);
        
        // TODO: figure out correct index to add at to avoid complete re-sort
        birthdays.insertElementAt(birthday, 0);
        sorter.sort(birthdays);
    }
    
    public Object getItemAt(int i) {
        return birthdays.elementAt(i);
    }

    public int getSize() {
        return birthdays.size();
    }
}
