/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.example.birthdays.view;

import com.nokia.example.birthdays.Visual;
import com.nokia.example.birthdays.data.Birthday;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Style;

/**
 * A renderer for a Birthday list item.
 */
public class BirthdayListItemRenderer
    extends Container
    implements ListCellRenderer {
    
    private Label nameLabel;
    private Label dateLabel;
    private Label descriptionLabel;
    
    public BirthdayListItemRenderer() {
        setPreferredW(Display.getInstance().getDisplayWidth());
        setPreferredH(50);
        
        initializeStyles();
        createLayout();
    }
    
    private void initializeStyles() {
        Style style = getStyle();
        style.setPadding(0, 0, 0, 0);
        style.setMargin(0, 0, 0, 0);
    
        nameLabel = new Label();
        style = nameLabel.getStyle();
        style.setFont(Visual.MEDIUM_FONT);

        dateLabel = new Label();
        style = dateLabel.getStyle();
        style.setFont(Visual.SMALL_BOLD_FONT);
        
        descriptionLabel = new Label();
        style = descriptionLabel.getStyle();
        style.setFont(Visual.SMALL_FONT);
    }
    
    private void createLayout() {
        Container dateContainer = new Container();
        dateContainer.setLayout(new BorderLayout());
        dateContainer.addComponent(BorderLayout.WEST, dateLabel);
        dateContainer.addComponent(BorderLayout.EAST, descriptionLabel);
        
        Container textContainer = new Container();
        textContainer.setLayout(new BorderLayout());
        textContainer.addComponent(BorderLayout.NORTH, nameLabel);
        textContainer.addComponent(BorderLayout.SOUTH, dateContainer);
        
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        setLayout(borderLayout);
        addComponent(BorderLayout.WEST, textContainer);
    }

    public Component getListCellRendererComponent(List list, Object object, int index, boolean isSelected) {
        final Birthday birthday = (Birthday) object;
        
        nameLabel.setText(birthday.getName() + " (" + birthday.getFormattedAgeOnNextBirthday() + ")");
        dateLabel.setText(birthday.getFormattedBirthDate());
        descriptionLabel.setText(birthday.getTimeUntilNextOccurrence());
        
        return this;
    }

    public Component getListFocusComponent(List list) {
        return null;
    }
}
