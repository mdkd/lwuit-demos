/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.listdemo.lists;

import com.nokia.example.listdemo.ListView;
import com.nokia.lwuit.components.PopupChoiceGroup;
import java.util.Vector;

/**
 * This is a view demonstrating how a multiple choice picker -component
 * is to be used.
 */
public class MultipleChoicePickerView extends ListView {

    private PopupChoiceGroup multiple;
    private String[] options = new String[] {
        "Option item 1",
        "Option item 2",
        "Option item 3",
        "Option item 4",
        "Option item 5"};
    private String info = "Help text goes here. This is a demonstration of Multi ChoicePicker with help text.";
    
    public MultipleChoicePickerView() {
        setTitle("Multiple Choice");
        multiple = new PopupChoiceGroup(
                "Multi ChoicePicker", info, options, PopupChoiceGroup.MULTIPLE);     
        addComponent(multiple);
        layoutContainer();
    }

    public int[] getSelectedIndices() {
        // note that the vector returned is populated with Strings
        Vector indices = multiple.getMultiSelectedIndexes();
        int size = indices.size();
        int[] retVal = new int[size];       
        
        for (int i=0; i<size; i++) {
            retVal[i] = Integer.parseInt((String)indices.elementAt(i));
        }

        return retVal;
    }

    public void setSelectedIndices(int[] indices) {
        for (int i=0; i<indices.length; i++) {
            multiple.setSelected(indices[i]);
        }
    }
    
}
