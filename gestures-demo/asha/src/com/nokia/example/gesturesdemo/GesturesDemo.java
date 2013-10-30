/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.gesturesdemo;

import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;

/**
 * The main MIDlet class containing the grid with the images
 */
public class GesturesDemo 
    extends MIDlet {
    private ImageGrid imageGrid;
    private boolean initialized = false;

    public void startApp() {
        if (!initialized) {
            Display.init(this);
            String keyboard = System.getProperty("com.nokia.keyboard.type");
            if (keyboard.equalsIgnoreCase("OnekeyBack") || keyboard.equalsIgnoreCase("None")) {  // Asha or a full touch device
                Display.getInstance().setForceFullScreen(true);
            }
            imageGrid = new ImageGrid();
            try {  // create an array of images for the grid
                Image[] images = new Image[6];
                images[0] = Image.createImage("/image1.png");
                images[1] = Image.createImage("/image2.png");
                images[2] = Image.createImage("/image3.png");
                images[3] = Image.createImage("/image4.png");
                images[4] = Image.createImage("/image5.png");
                images[5] = Image.createImage("/image6.png");
                for (int i = 0; i < images.length; i++) {
                    ImageCell imageCell = new ImageCell(images[i]);
                    imageGrid.addComponent(imageCell);
                }
            } 
            catch (IOException ex) {
            }
        }

        initialized = true;
        imageGrid.show();
    }
    /**
     * Called when the MIDlet is set to the background by an external event 
     */
    public void pauseApp() {
        // Empty implementation
    }
    
    /**
     * Called when the MIDlet terminates
     */
    public void destroyApp(boolean unconditional) {
        // Empty implementation
    }
}
