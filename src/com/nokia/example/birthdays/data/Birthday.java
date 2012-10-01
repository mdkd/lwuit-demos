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

import java.util.Calendar;
import java.util.Date;

public class Birthday {
    
    private String name;
    private Date birthday;

    public Birthday(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String toString() {
        return name + " (" + birthday + ")";
    }
    
    /**
     * Pretty-print how long it is until the birthday,
     * e.g. "today" or "5 days".
     *
     * @return Pretty-printed birthday as a String
     */
    public String getTimeUntilNextOccurrence() {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        c.setTime(birthday);
        c.set(Calendar.YEAR, currentYear);       
        long then = c.getTime().getTime() / 1000;
        
        // If birthday already occurred this year, consider the next year
        long now = new Date().getTime() / 1000;
        if (then < now) {
            // 60 seconds * 60 minutes * 24 hours * 365 days
            then += 31536000; 
        }

        // Next, calculate and pretty-print the time until the birthday
        long timeUntilBirthday = then - now;
        if (timeUntilBirthday < 86400) {
            return "today";
        }
        
        long units = 0;
        String unit = "";
        
        // Less than a month -> "x days"
        if (timeUntilBirthday < 2592000) {
            units = timeUntilBirthday / 86400;
            unit = (units > 1 ? "" + units : "a") +
                " day" + (units > 1 ? "s" : "");
        }
        // Less than a year -> "x months" (11 full months at most)
        else if (timeUntilBirthday < 31536000) {
            units = Math.min(11, timeUntilBirthday / 2592000);
            unit = (units > 1 ? "" + units : "a") +
                " month" + (units > 1 ? "s" : "");
        }
        
        return "in " + unit;
    }    
}
