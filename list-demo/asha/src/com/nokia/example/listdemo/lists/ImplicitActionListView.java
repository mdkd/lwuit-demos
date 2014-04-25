/**
* Copyright (c) 2012-2014 Microsoft Mobile. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.listdemo.lists;

import com.nokia.example.listdemo.Compatibility;
import com.nokia.example.listdemo.DemoMidlet;
import com.nokia.example.listdemo.ListView;
import com.nokia.lwuit.templates.list.NokiaListCellRenderer;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Rectangle;
import com.sun.lwuit.list.DefaultListModel;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.list.ListModel;

/**
 * This is a view demonstrating how a list with an implicit action
 * is to be used.
 */
public class ImplicitActionListView extends ListView {

    public ImplicitActionListView() {
        setTitle("Implicit + action");

        // Create list model
        ListModel model = new DefaultListModel();
        for (int i = 0; i < MAX_ITEMS; i++) {
            String text = LIST_ITEM_TEXT + (i + 1);
            model.addItem(text);
        }

        // Create list based on the model
        list = new List(model);

        // Create and set list renderer
        ListCellRenderer renderer = new NokiaListCellRenderer();
        list.setRenderer(renderer);

        // Add ActionListener for the list
        list.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        showSelectedDialog();
                    }
                });

        // Add the list
        addComponent(list);

        // Create and add DefaultCommand for adding new list items to the list model
        Command addCommand = Compatibility.supportsIconCommands()
                ? new Command("add", DemoMidlet.loadImage("/add_icon.png"), 0)
                : new Command("Add");
        if (Display.getInstance().isTouchScreenDevice()) {
            setDefaultCommand(addCommand);
        }
        addCommand(addCommand);

        addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                list.getModel().addItem(
                        LIST_ITEM_TEXT
                        + (list.getModel().getSize() + 1));
                layoutContainer();
                list.scrollRectToVisible(new Rectangle(0, list.getHeight(), 0, 0));
            }
        });
        layoutContainer();
    }
}
