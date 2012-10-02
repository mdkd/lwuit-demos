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
 * Access and create new Birthdays via the PIM API.
 * 
 * Does not care about the big picture, only relays data.
 */
public class PIMContactHandler {
    
    private final long NOW_MILLIS = new Date().getTime();
    private final int FIRST = Contact.NAME_GIVEN;
    private final int LAST = Contact.NAME_FAMILY;

    private static PIMContactHandler instance;    

    public static PIMContactHandler getInstance() {
        if (instance == null) {
            instance = new PIMContactHandler();
        }
        return instance;
    }

    /**
     * Create a Contact with birthday in the phone memory.
     * 
     * @param name Name of birthday hero
     * @param birthday Date of birth
     */
    public void addBirthday(Birthday birthday) {
        System.out.println("Adding birthday via PIM: " + birthday);
        
        ContactList contactList;
        try {
            contactList = (ContactList)
                PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.WRITE_ONLY);
            
            String[] names =
                new String[contactList.stringArraySize(Contact.NAME)];            
            names[FIRST] = birthday.getName();
            
            Contact contact = contactList.createContact();
            contact.addStringArray(Contact.NAME, PIMItem.ATTR_NONE, names);
            contact.addDate(Contact.BIRTHDAY, PIMItem.ATTR_NONE,
                birthday.getBirthday().getTime());
            
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
        ContactList contactList = null;
        Enumeration contactItems = null;
        
        try {
            PIM pim = PIM.getInstance();
            contactList =
                (ContactList) pim.openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
            contactItems = contactList.items();
        }
        catch (PIMException pime) {
            System.out.println("Could not open PIM contact list: " +
                pime.getMessage());
        }
        
        // Import contact list elements into Birthday objects
        Vector birthdays = new Vector();
        Contact contact = null;
        while (contactItems.hasMoreElements()) {
            contact = (Contact) contactItems.nextElement();
            
            Birthday birthday = createBirthdayFromContact(contact);
            if (birthday != null) {
                birthdays.addElement(birthday);
            }
        }
        
        try {
            contactList.close();
        }
        catch (Exception e) {}
        
        return birthdays;
    }
    
    private Birthday createBirthdayFromContact(Contact contact) {
        // To make a sensible display item, the Contact needs to have both
        // a name and a birthday
        if (contact.countValues(Contact.BIRTHDAY) < 1 ||
            contact.countValues(Contact.NAME) < 1) {
            return null;
        }
        
        String[] names =
            contact.getStringArray(Contact.NAME, Contact.ATTR_NONE);

        String name =
            (names[FIRST] != null ? names[FIRST] + " " : "") +
            (names[LAST] != null ? names[LAST] : "");

        // Only include birthdays for people that have been born
        long birthdayMillis = contact.getDate(Contact.BIRTHDAY, 0);
        if (birthdayMillis > NOW_MILLIS) {
            return null;
        }

        return new Birthday(name, new Date(birthdayMillis));
    }
}
