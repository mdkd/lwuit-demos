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

import com.nokia.example.birthdays.BirthdayMidlet.BackListener;
import com.nokia.example.birthdays.data.PIMContactHandler;
import com.nokia.example.birthdays.view.BirthdayListView.BirthdayInsertionListener;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;

public class ContactListView extends Form {
    
    private List contactList;
    private Command backCommand;
    private ContactListModel listModel;
    
    private BirthdayInsertionListener insertionListener;
    private BackListener backListener;
    
    public ContactListView(BirthdayInsertionListener insertionListener, BackListener backListener) throws PIMContactHandler.PIMNotAccessibleException {
        super("Choose contact");
        
        this.insertionListener = insertionListener;
        this.backListener = backListener;
        addCommands();
        createList();
    }
    
    private void createList() throws PIMContactHandler.PIMNotAccessibleException {
        contactList = new List();
        listModel = ContactListModel.getInstance();
        contactList.setModel(listModel);
        addComponent(contactList);
    }
    
    private void addCommands() {
        backCommand = new Command("Exit") {
            public void actionPerformed(ActionEvent e) {
                backListener.backCommanded();
            }
        };
        addCommand(backCommand);
        setBackCommand(backCommand);
    }
}
