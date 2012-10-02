/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.example.birthdays.data;

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
    
    public static BirthdayListModel getInstance() {
        if (instance == null) {
            instance = new BirthdayListModel();
        }
        return instance;
    }
    
    private Vector birthdays;
    
    private BirthdayListModel() {
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
