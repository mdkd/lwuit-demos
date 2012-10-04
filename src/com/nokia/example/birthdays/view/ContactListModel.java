package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.data.PIMContactHandler;
import com.nokia.example.birthdays.data.PIMContactHandler.PIMNotAccessibleException;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListModel;
import java.util.Vector;
import javax.microedition.pim.Contact;

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
    
    private ContactListModel() throws PIMContactHandler.PIMNotAccessibleException {
        pimHandler = PIMContactHandler.getInstance();
        contactsWithoutBirthday = pimHandler.getContactsWithoutBirthday();
    }
    
    public Object getItemAt(int i) {
        if (i == 0) {
            return "Create new contact";
        }
        
        Contact c = (Contact) contactsWithoutBirthday.elementAt(i - 1);
        String name = c.getString(Contact.FORMATTED_NAME, Contact.ATTR_NONE);
        return name;
    }
    
    public int getSize() {
        return contactsWithoutBirthday.size() + 1;
    }
}
