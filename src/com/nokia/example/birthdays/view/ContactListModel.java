package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.data.PIMContactHandler;
import com.nokia.example.birthdays.data.PIMNotAccessibleException;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import java.util.Vector;

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
        if (i == 0) {
            return null;
        }        
        return contactsWithoutBirthday.elementAt(i - 1);
    }
    
    public int getSize() {
        return contactsWithoutBirthday.size() + 1;
    }
    
    public void refresh() throws PIMNotAccessibleException {
        contactsWithoutBirthday = pimHandler.getContactsWithoutBirthday();        
    }
}
