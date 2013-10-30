/**
* Copyright (c) 2012-2013 Nokia Corporation. All rights reserved.
* Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
* Oracle and Java are trademarks or registered trademarks of Oracle and/or its
* affiliates. Other product and company names mentioned herein may be trademarks
* or trade names of their respective owners. 
* See LICENSE.TXT for license information.
*/
package com.nokia.example.gesturesdemo;

import com.nokia.lwuit.GestureHandler;
import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Graphics;
import com.sun.lwuit.Image;
import com.sun.lwuit.Painter;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.geom.Rectangle;

/*
 * Represents the full screen image view, after the user has
 * tapped and selected an image from the grid
 */
public class ImageView 
    extends Form {

    private Image image;
    private ImageGrid imageGrid;
    private Command backCommand;
    private double zoom;
    private int panX;
    private int panY;
    private int scaledImageWidth;
    private int scaledImageHeight;
    private static final double MAX_ZOOM_RATIO = 2.0D;
    private static final long MAX_DOUBLE_TAP_DELTA = 500L;

    public ImageView(Image image, ImageGrid imageGrid) {
        this.image = image;
        this.imageGrid = imageGrid;
        this.setScrollable(false);
        this.setGlassPane(new ImagePainter());

        zoom = 1;
        ImageViewGestureHandler gestureHandler = new ImageViewGestureHandler();
        setupGestureHandler(gestureHandler);
        backCommand = new Command("Back") {
            public void actionPerformed(ActionEvent e) {
                exitImageView();
            }
        };
        setBackCommand(backCommand);
        
        getTitleArea().setPreferredH(0);
        
        getStyle().setBgColor(0x000000, true);
        getStyle().setBgTransparency(255);
    }
    
    public void onShowCompleted() {
        getMenuBar().setVisible(true);
    }
    
    /**
     * Shows next image.
     */
    private void showNextImage() {
        setImageAtOffset(1);
    }

    /**
     * Shows previous image.
     */
    private void showPrevImage() {
        setImageAtOffset(-1);
    }
    
    // just to prevent "leaking this from constructor" warning
    private void setupGestureHandler(GestureHandler handler) {
        GestureHandler.setFormGestureHandler(this, handler);
    }
    
    /*
     * Draws the scaled image 
     */
    private class ImagePainter implements Painter {

        public void paint(Graphics g, Rectangle r) {
            calculateImageDimensions();
            int x = (getWidth() - scaledImageWidth) / 2 + panX;
            int y = (getHeight() - scaledImageHeight) / 2 + panY;
            g.drawImage(image, x, y, scaledImageWidth, scaledImageHeight);
            
        }
    }

    /**
     * Displays an image in the image grid located at specified offset relative
     * to the currently displayed image.
     *
     * @param indexOffset the index offset
     */
    private void setImageAtOffset(int indexOffset) {
        Container cp = imageGrid.getContentPane();
        int i = 0;
        for (; i < cp.getComponentCount(); i++) {
            if (cp.getComponentAt(i) instanceof ImageCell) {
                ImageCell ic = (ImageCell) cp.getComponentAt(i);
                if (ic.getImage() == image) {
                    break;
                }
            }
        }
        i += indexOffset;
        if (i < 0) {
            i = cp.getComponentCount() - 1;
        }
        if (i >= cp.getComponentCount()) {
            i = 0;
        }
        ImageCell ic = (ImageCell) cp.getComponentAt(i);
        transitionToImage(ic.getImage(), indexOffset < 0);
    }

    /**
     * Creates a slide transition from the current image to another.
     *
     * @param image the image to be shown.
     * @param direction whether transition is left-to-right or right-to-left.
     */
    private void transitionToImage(Image image, boolean direction) {
        getMenuBar().setVisible(false);
        Transition out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, direction, 500);
        setTransitionOutAnimator(out);
        ImageView imageView = new ImageView(image, imageGrid);
        imageView.getMenuBar().setVisible(false);
        imageView.show();
    }

    /**
     * Creates a fade transition to the image grid.
     */
    private void exitImageView() {
        Transition t = CommonTransitions.createFade(500);
        imageGrid.setTransitionInAnimator(t);
        imageGrid.show();
    }

    /**
     * Calculates the image dimensions when it's scaled to screen size and
     * zoomed according to current zoom level.
     */
    private void calculateImageDimensions() {
        double w = (double) image.getWidth();
        double h = (double) image.getHeight();
        double aspectRatio = w / h;
        double scale;
        if (aspectRatio > 1) { // portrait
            scale = getWidth() / w;
        } 
        else {  // landscape
            scale = getHeight() / h;
        }
        scale *= zoom;
        scaledImageWidth = (int) (w * scale);
        scaledImageHeight = (int) (h * scale);
    }

    /**
     * Adjusts the zoom level according to pinch distance.
     *
     * @param pixels the pinch distance in pixels.
     */
    private void zoom(int pixels) {
        double scale = (double) (scaledImageHeight + pixels) / scaledImageHeight;
        zoom(zoom * scale);
    }

    /**
     * Sets the zoom ratio.
     *
     * @param zoom the zoom ratio.
     */
    private void zoom(double zoom) {
        if (zoom < 1) {
            zoom = 1;
        }
        if (zoom > MAX_ZOOM_RATIO) {
            zoom = MAX_ZOOM_RATIO;
        }
        double scale = zoom / this.zoom;
        this.zoom = zoom;
        calculateImageDimensions();
        pan((int) (panX * scale), (int) (panY * scale));
    }

    /*
     * checks if the flick is performed on the vertical axis
     */
    private boolean isNotVertical(GestureEvent ge) {
        boolean result = true;
        
        if(ge.getFlickDirection() > 0.6 && ge.getFlickDirection() < 2.3) {  // top to bottom flick
            result = false;
        }
        else if(ge.getFlickDirection() < -1.18 && ge.getFlickDirection() > -2.5) {  // bottom to top flick
            result = false;
        }
        
        return result;        
    }
    /**
     * Pans the image.
     *
     * @param x amount of pixels to pan in x axis.
     * @param y amount of pixels to pan in y axis.
     */
    private void pan(int x, int y) {
        panX = panY = 0;
        int maxPan = 0;
        if (scaledImageWidth > getWidth()) {
            maxPan = (scaledImageWidth - getWidth()) / 2;
        }

        panX = x;
        if (panX > maxPan) {
            panX = maxPan;
        } 
        else if (panX < -maxPan) {
            panX = -maxPan;
        }

        if (scaledImageHeight > getHeight()) {
            maxPan = (scaledImageHeight - getHeight()) / 2;
        } 
        else {
            maxPan = 0;
        }

        panY = y;
        if (panY > maxPan) {
            panY = maxPan;
        } 
        else if (panY < -maxPan) {
            panY = -maxPan;
        }

        repaint();
    }

    private class ImageViewGestureHandler extends GestureHandler {

        private long previousTapTimeMs;
        private boolean pinching = false;

        public void gestureAction(GestureEvent ge) {

            switch (ge.getType()) {
                case GestureInteractiveZone.GESTURE_RECOGNITION_START: {
                    pinching = false;
                    break;
                }
                case GestureInteractiveZone.GESTURE_DRAG: {
                    pan(panX + ge.getDragDistanceX(), panY + ge.getDragDistanceY());
                    break;
                }
                case GestureInteractiveZone.GESTURE_FLICK: {
                    if (zoom == 1.0 && !pinching) {
                        if (ge.getFlickSpeedX() < 0 && isNotVertical(ge)) {
                            showNextImage();
                        } 
                        else if (ge.getFlickSpeedX() > 0 && isNotVertical(ge)) {
                            showPrevImage();
                        }
                    }
                    break;
                }
                case GestureInteractiveZone.GESTURE_TAP: {
                    long tapTimeMs = System.currentTimeMillis();
                    long delta = tapTimeMs - previousTapTimeMs;
                    if (delta < MAX_DOUBLE_TAP_DELTA && !pinching) {
                        if (zoom > 1.0) {
                            zoom(1.0);
                        } 
                        else {
                            zoom(MAX_ZOOM_RATIO);
                        }
                        previousTapTimeMs = 0;
                        break;
                    }
                    previousTapTimeMs = System.currentTimeMillis();
                    break;
                }
                case GestureInteractiveZone.GESTURE_PINCH: {
                    pinching = true;
                    zoom(ge.getPinchDistanceChange());
                    break;
                }
                default:
                    break;

            }
        }
    }
}
