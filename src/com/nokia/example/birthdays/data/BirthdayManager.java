/*
 */
package com.nokia.example.birthdays.data;

import java.util.Date;
import java.util.Vector;

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
        return birthdays;
    }
}
