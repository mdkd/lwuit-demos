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
import com.nokia.lwuit.components.PopupChoiceGroup;

/**
 * This is a view demonstrating how a single choice picker -component
 * is to be used.
 */
public class SingleChoicePickerView extends ListView {

    private PopupChoiceGroup single;
    private String[] choices = new String[] {"Option item 1", "Option item 2", "Option item 3"};

    public SingleChoicePickerView() {
        setTitle("Single Choice");
        single = new PopupChoiceGroup(
                "Single ChoicePicker", null, choices, PopupChoiceGroup.SINGLE);
        addComponent(single);
        layoutContainer();
    }

    public int[] getSelectedIndices() {
        return new int[]{single.getSelectedIndex()};
    }

    public void setSelectedIndices(int[] indices) {
        single.setSelected(indices[0]);
    }   
}
