/**
* Copyright (c) 2012-2014 Microsoft Mobile. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.listdemo;

import com.nokia.example.listdemo.lists.ImplicitActionListView;
import com.nokia.example.listdemo.lists.ImplicitListView;
import com.nokia.example.listdemo.lists.MultipleChoicePickerView;
import com.nokia.example.listdemo.lists.SingleChoicePickerView;
import com.nokia.example.listdemo.lists.ThumbnailsListView;
import com.nokia.example.listdemo.lists.TruncatedListView;
import com.nokia.example.listdemo.lists.WrappedListView;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class DemoMidlet
        extends MIDlet {

    private Form mainForm;
    private List mainMenuList;
    private Form[] listViews;
    private static DemoMidlet instance;
    
    private boolean initialized = false;

    /**
     * @see MIDlet#startApp()
     */
    protected void startApp()
            throws MIDletStateChangeException {

        if (!initialized) {
            Display.init(this); // initialize LWUIT Display        
            instance = this;

            // Initialize demos - a Vector is used to grow container
            // holding references to list views applicable to each
            // platform.
            Vector tempViewContainer = new Vector();
            tempViewContainer.addElement(new ImplicitListView());
            tempViewContainer.addElement(new ImplicitActionListView());
            tempViewContainer.addElement(new TruncatedListView());
            tempViewContainer.addElement(new WrappedListView());
            tempViewContainer.addElement(new ThumbnailsListView());
            tempViewContainer.addElement(new SingleChoicePickerView());
            if(Display.getInstance().getDeviceType() == Display.ASHA_DEVICE) {
                tempViewContainer.addElement(new MultipleChoicePickerView());
            }
            
            listViews = new Form[tempViewContainer.size()];
            
            for (int i=0; i<tempViewContainer.size(); i++) {
                listViews[i] = (Form) tempViewContainer.elementAt(i);
            }

            // Create main view
            mainForm = new Form(Compatibility.toLowerCaseIfFT("List examples"));
            mainForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

            mainMenuList = new List(listViews);
            mainMenuList.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    listViews[mainMenuList.getSelectedIndex()].show();
                }
            });
            mainForm.addComponent(mainMenuList);

            Command backCommand = new Command("Exit") {
                public void actionPerformed(ActionEvent e) {
                    notifyDestroyed();
                }
            };
            mainForm.setBackCommand(backCommand);
        }

        initialized = true;
        mainForm.show();
        mainForm.revalidate();
    }

    /**
     * @see MIDlet#pauseApp()
     */
    protected void pauseApp() {
    }

    /**
     * @see MIDlet#destroyApp(boolean)
     */
    protected void destroyApp(boolean unconditional)
            throws MIDletStateChangeException {
    }

    /**
     * This method returns the application main view.
     *
     * @return main <code>Form</code>
     */
    public static Form getMainForm() {
        return instance.mainForm;
    }

    /**
     * Loads the image in the resources directory with the given path.
     *
     * @param path Path of the image.
     * @return Image found with the given path
     */
    public static Image loadImage(String path) {
        try {
            // load image
            return Image.createImage(path);
        } catch (IOException ioe) {
        }
        return null;
    }
}
