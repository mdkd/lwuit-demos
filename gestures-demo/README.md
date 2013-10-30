LWUIT Gestures Demo
===================

LWUIT Gestures Demo is a pseudo photo gallery application for Nokia Asha
software platform and Series 40 phones created with LWUIT library. It shows
developers how to use different types of gestures in LWUIT applications.
Additionally it demonstrates transitions, drag & drop and image scaling.

The application is hosted in GitHub:
https://github.com/nokia-developer/lwuit-demos/tree/master/gestures-demo


1. Usage
-------------------------------------------------------------------------------

LWUIT Gesture Demo consists of two different views: grid view for browsing and
arranging images, and image view for viewing, zooming and panning images.

Grid view:

* Tap an image to view it in image viewer.
* Long tap the screen to enter edit mode where images can be repositioned by
  dragging.

Image view:

* Pinch the image to zoom in/out.
* Drag the zoomed image to pan it horizontally or vertically.
* Double-tapping the image zooms to minimum or maximum level depending on the
  current zoom level.
* Flick gesture switches to next or previous image depending on the flick
  direction.


2. Prerequisites
-------------------------------------------------------------------------------

* Java ME basics
* LWUIT basics


3. Important classes
-------------------------------------------------------------------------------

* `GesturesDemo`:  The midlet class that initialises the display and starts the
  application.
* `ImageGrid`: Displays a grid of images.
* `ImageView`: Displays an image.


3.1 Design considerations
-------------------------

The application starts in GesturesDemo class where the images are loaded from
resources and ImageGrid view gets created and shown.

ImageGrid extends Form and displays a grid of ImageCell components. Each
ImageCell displays an image scaled to the cell size. ImageGrid has it's own
layout manager, ImageGridLayout, that dynamically positions and resizes the
components to a grid layout with defined amount of columns. 

ImageGrid has a GestureHandler that listens for tap and long press gestures.
On long press the ImageGrid switches to edit mode where the components can be
rearranged by dragging. This is implemented by using LWUIT drag and drop. If
edit mode is not active, tapping an image shows it in ImageView.

ImageView displays the image scaled to screen size and adjusted to zoom and
pan levels. ImageView has a GestureHandler for listening to drag, pinch, tap
and flick gestures for panning, zooming and flicking the images. When flicking
from an image to another a transition animation is shown. The animation is
implemented by using LWUIT transitions.


4. Compatibility
-------------------------------------------------------------------------------

LWUIT Gesture Demo is compatible with Nokia Asha software platform and Series 40
full touch phones. Max heap memory of the device needs to be at least 2 MB.

Tested on Nokia Asha 501, Nokia Asha 311 and Nokia Asha 306.

Developed with Nokia Asha SDK 1.0 and Netbeans 7.1.


4.1 Required capabilities
-------------------------

CLDC 1.1, MIDP 2.0


4.2 Known issues
----------------

* Fast scrolling or zooming may cause an occasional OutOfMemoryException.
* Color and layout issues in touch and type devices.


5. Building, installing, and running the application
-------------------------------------------------------------------------------

The example has been made with NetBeans 7.1 and Nokia SDK 2.0. The project can 
be easily opened in NetBeans by selecting 'Open Project' from the File menu 
and selecting the application.

Before opening the project, make sure the Nokia SDK 2.0 is installed and added 
to NetBeans. Ensure that LWUIT for Series 40 library is added in the Libraries 
section. Building is done by selecting 'Build main project'.

Installing the application on a phone can be done by transferring the JAR file 
via Nokia Ovi Suite or via Bluetooth.


6. License
-------------------------------------------------------------------------------

See the license text file delivered with this project. The license file is 
also available online at 
https://github.com/nokia-developer/lwuit-demos/blob/master/LICENSE.TXT


7. Related documentation
-------------------------------------------------------------------------------

LWUIT Developer's Library
* http://developer.nokia.com/Resources/Library/LWUIT/#!index.html

Nokia SDK for Java
* http://www.developer.nokia.com/Develop/Java/Tools/


8. Version history
-------------------------------------------------------------------------------
* 1.0    First release (at Nokia Developer projects only).
