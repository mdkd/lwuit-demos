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
    private Calendar birthday = Calendar.getInstance();

    public Birthday(String name, Date date) {
        this.name = name;
        this.birthday.setTime(date);
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return birthday.getTime();
    }

    public String toString() {
        return name + " (" + birthday.getTime() + ")";
    }
}
