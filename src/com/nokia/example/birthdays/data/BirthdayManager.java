/*
 */
package com.nokia.example.birthdays.data;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;

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
    }

    public Vector getBirthdays() {        
        PIM pim = PIM.getInstance();
        
        ContactList contactList = null;
        Enumeration contactItems = null;
        try {
            contactList = (ContactList) pim.openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);
            contactItems = contactList.items();
        } catch (PIMException pime) {
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
                System.out.println(birthday);
                birthdays.addElement(birthday);
            }
        }
        
        try {
            contactList.close();
        } catch (Exception e) {}
        
        return birthdays;
    }
}
