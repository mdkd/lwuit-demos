/**
* Copyright (c) 2012-2014 Microsoft Mobile. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.data.PIMContactHandler;
import com.nokia.example.birthdays.data.PIMNotAccessibleException;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import java.util.Vector;

/**
 * A straight-forward model for contacts.
 */
class ContactListModel
    extends DefaultListModel
    implements ListModel {
    
    private PIMContactHandler pimHandler;
    private Vector contactsWithoutBirthday;
    
    private static ContactListModel instance;
    
    public static ContactListModel getInstance() throws PIMNotAccessibleException {
        if (instance == null) {
            instance = new ContactListModel();
        }
        return instance;
    }
    
    private ContactListModel() throws PIMNotAccessibleException {
        pimHandler = PIMContactHandler.getInstance();
        contactsWithoutBirthday = pimHandler.getContactsWithoutBirthday();
    }
    
    public Object getItemAt(int i) {
        // The first item in the list will be 'Create new contact' pseudo-item,
        // so return null to signal there is no Contact associated to it.
        if (i == 0) {
            return null;
        }
        
        // For following indices, return elements from the contact list normally
        return contactsWithoutBirthday.elementAt(i - 1);
    }
    
    public int getSize() {
        return contactsWithoutBirthday.size() + 1;
    }
    
    public void refresh() throws PIMNotAccessibleException {
        contactsWithoutBirthday = pimHandler.getContactsWithoutBirthday();
    }
}
