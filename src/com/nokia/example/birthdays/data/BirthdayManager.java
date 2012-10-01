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

public class BirthdayManager {
    private static BirthdayManager instance;
    private Vector birthdays = new Vector();

    public static BirthdayManager getInstance() {
        if (instance == null) {
            instance = new BirthdayManager();
        }
        return instance;
    }

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
            contact.commit();
            contactList.close();
        }
        catch (PIMException pime) {
            System.out.println("Error saving new Contact: " + pime.getMessage());
        }
    }

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
        Birthday birthday = null;
        
        while (contactItems.hasMoreElements()) {
            contact = (Contact) contactItems.nextElement();
            
            if (contact.countValues(Contact.BIRTHDAY) > 0 && contact.countValues(Contact.NAME) > 0) {
                String[] nameArray = contact.getStringArray(Contact.NAME, Contact.ATTR_NONE);
                String name =
                    (nameArray[Contact.NAME_GIVEN] != null ? nameArray[Contact.NAME_GIVEN] + " " : "") + nameArray[Contact.NAME_FAMILY];
                Date birthDate = new Date(contact.getDate(Contact.BIRTHDAY, 0));
                
                birthday = new Birthday(name, birthDate);                
                birthdays.addElement(birthday);
            }
        }
        
        try {
            contactList.close();
        }
        catch (Exception e) {}
        
        return birthdays;
    }
}
