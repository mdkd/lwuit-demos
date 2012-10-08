/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *
 * See LICENSE.TXT for license information.
 */
package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.BirthdayMidlet;
import com.nokia.example.birthdays.BirthdayMidlet.ExitListener;
import com.nokia.example.birthdays.data.PIMNotAccessibleException;
import com.nokia.example.birthdays.util.Commands;
import com.nokia.example.birthdays.util.Compatibility;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import javax.microedition.pim.Contact;

public class BirthdayListView extends Form {

    private List birthdayList;
    private Command addCommand;
    private Command exitCommand;
    private BirthdayInsertionListener birthdayListener;
    private BirthdayListModel listModel;

    public interface BirthdayInsertionListener {
        public void birthdayInsertionRequested(Contact contact);
    }

    public BirthdayListView(BirthdayInsertionListener birthdayInsertionListener,
        final ExitListener exitListener) throws PIMNotAccessibleException {
        super("Birthdays");
        this.birthdayListener = birthdayInsertionListener;
        
        addCommands();
        createList();
    }
    
    private void createList() throws PIMNotAccessibleException {        
        birthdayList = new List();
        
        listModel = BirthdayListModel.getInstance();
        birthdayList.setModel(listModel);
        birthdayList.setRenderer(new BirthdayListItemRenderer());
        
        addComponent(birthdayList);
    }
    
    private void addCommands() {
        Image addCommandImage = null;        
        if (Compatibility.isFullTouch()) {
            System.out.println("isFullTouch");
            addCommandImage = Commands.loadImage(Commands.ADD_COMMAND_IMAGE);
            System.out.println("addCommandImage: " + addCommandImage);
        } else {
            System.out.println("not full touch");
        }
        
        addCommand = new Command("Add", addCommandImage) {
            public void actionPerformed(ActionEvent e) {
                birthdayListener.birthdayInsertionRequested(null);
            }
        };
        addCommand(addCommand);
        setDefaultCommand(addCommand);

        exitCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
                BirthdayMidlet.getInstance().notifyDestroyed();
            }
        };
        addCommand(exitCommand);
        setBackCommand(exitCommand);        
    }
}
