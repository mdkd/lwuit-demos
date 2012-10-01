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

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.PIMItem;

/**
 * Access and create new Birthdays.
 */
public class BirthdayManager {
    
    private final long NOW_MILLIS = new Date().getTime();

    private static BirthdayManager instance;
    private Vector birthdays = new Vector();

    public static BirthdayManager getInstance() {
        if (instance == null) {
            instance = new BirthdayManager();
        }
        return instance;
    }

    /**
     * Create a new Birthday (Contact).
     * 
     * @param name Name of birthday hero
     * @param birthday Date of birth
     */
    public void addBirthday(String name, Date birthday) {
        if (birthdays == null) {
            birthdays = new Vector();
        }
        
        System.out.println("Adding birthday: " + name + ", " + birthday);
        birthdays.addElement(new Birthday(name, birthday));
        
        ContactList contactList;
        try {
            contactList = (ContactList)
                PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.WRITE_ONLY);
            
            String[] names =
                new String[contactList.stringArraySize(Contact.NAME)];
            
            names[Contact.NAME_GIVEN] = name;
            
            Contact contact = contactList.createContact();
            contact.addStringArray(Contact.NAME, PIMItem.ATTR_NONE, names);
            contact.addDate(Contact.BIRTHDAY, PIMItem.ATTR_NONE, birthday.getTime());
            
            // Save Contact and close the PIM access
            contact.commit();
            contactList.close();
        }
        catch (PIMException pime) {
            System.out.println("Error saving new Contact: " + pime.getMessage());
        }
    }

    /**
     * Read contacts and create a Vector of Birthdays.
     * 
     * @return Vector object of birthdays for phone contacts
     */
    public Vector getBirthdays() {        
        PIM pim = PIM.getInstance();
        
        ContactList contactList = null;
        Enumeration contactItems = null;
        try {
            contactList = (ContactList) pim.openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
            contactItems = contactList.items();
        }
        catch (PIMException pime) {
            System.out.println("Could not open PIM contact list: " + pime.getMessage());
        }
        
        Contact contact = null;
        int first = Contact.NAME_GIVEN;
        int last = Contact.NAME_FAMILY;
        
        // Import contact list elements into Birthday objects
        while (contactItems.hasMoreElements()) {
            contact = (Contact) contactItems.nextElement();
            
            // To make a sensible display item, the Contact needs to have both
            // a name and a birthday
            if (contact.countValues(Contact.BIRTHDAY) > 0 &&
                contact.countValues(Contact.NAME) > 0) {
                String[] names =
                    contact.getStringArray(Contact.NAME, Contact.ATTR_NONE);
                
                String name =
                    (names[first] != null ? names[first] + " " : "") +
                    (names[last] != null ? names[last] : "");

                // Only include birthdays for people that have been born
                long birthdayMillis = contact.getDate(Contact.BIRTHDAY, 0);
                if (birthdayMillis < NOW_MILLIS) {
                    Date birthday = new Date(birthdayMillis);                
                    birthdays.addElement(new Birthday(name, birthday));
                }
            }
        }
        
        try {
            contactList.close();
        }
        catch (Exception e) {}
        
        return birthdays;
    }
}
