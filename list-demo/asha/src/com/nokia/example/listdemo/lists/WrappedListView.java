/**
* Copyright (c) 2012-2014 Microsoft Mobile. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.listdemo.lists;

import com.nokia.example.listdemo.ListView;
import com.sun.lwuit.Button;
import com.sun.lwuit.Container;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;

/**
 * This is a view demonstrating how a wrapped list is to be used.
 */
public class WrappedListView extends ListView {

    private int selectedIndex;

    public WrappedListView() {
        setTitle("Wrapped");

        // Create list by stacking TextAreas
        for (int i = 0; i < MAX_ITEMS; i++) {
            final int index = i;

            // Create a Container for the TextArea
            Container container = new Container(new BorderLayout());

            container.setUIID("List");
            
            // Delegate all incoming events to a Button component
            Button button = new Button();
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectedIndex = index;
                    showSelectedDialog();
                }
            });
            
            // Add button to the container to gain traversal functionality
            container.addComponent(BorderLayout.NORTH, button);
            button.setVisible(false);
            
            container.setLeadComponent(button);

            // Create the TextArea and add it to the Container
            String text = LIST_ITEM_TEXT + (i + 1) + LONG_TEXT;
            TextArea textArea = new TextArea();
            textArea.setText(text);
            textArea.setUIID("ListItem");
            textArea.setEditable(false);
            textArea.setRows(2);
            textArea.setGrowByContent(true);
            container.addComponent(BorderLayout.CENTER, textArea);

            // Finally add the Container to the Form
            addComponent(container);
        }

        layoutContainer();
    }

    public int[] getSelectedIndices() {
        return new int[]{selectedIndex};
    }

    public void setSelectedIndices(int[] indices) {
        if (indices.length > 0) {
            selectedIndex = indices[0];
        }
    }
}
