/*
 */
package com.nokia.example.birthdays.data;

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
        return name + " " + birthday;
    }
}
