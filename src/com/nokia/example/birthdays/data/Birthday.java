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
    public String prettyPrint() {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        c.setTime(birthday);
        c.set(Calendar.YEAR, currentYear);
       
        long then = c.getTime().getTime() / 1000;
        long now = new Date().getTime() / 1000;
        
        // If already occurred this year, consider the next year
        if (then < now) {
            // 60 seconds * 60 minutes * 24 hours * 365 days
            then += 31536000; 
        }                
        long timeUntilBirthday = then - now;
        
        String result = "";
        long units = 0;
        String unit = "";

        if (timeUntilBirthday < 86400) {
            return "today";
        }
        else if (timeUntilBirthday < 2592000) {
            // "x days"
            units = timeUntilBirthday / 86400;
            unit = (units > 1 ? "" + units : "a") + " day" + (units > 1 ? "s" : "");
        }
        else if (timeUntilBirthday < 31536000) {
            // "x months" (11 full months at most)
            units = Math.min(11, timeUntilBirthday / 2592000);
            unit = (units > 1 ? "" + units : "a") + " month" + (units > 1 ? "s" : "");
        }

        result = "in " + unit;
        return result;
    }    
}
