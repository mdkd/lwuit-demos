LWUIT Category Bar Demo
=======================

An example application to demonstrate how to use a category bar together with
the LWUIT framework.

This example application is hosted in GitHub:
* https://github.com/nokia-developer/lwuit-demos/category-bar-demo/


1. Usage
-------------------------------------------------------------------------------

The MIDlet starts in the "add content" view demonstrating how the action #1
button can be used with category bar and LWUIT. The other two examples can be 
reached from the category bar that is visible throughout the demo.

LWUIT, views and a category bar are initialised when the MIDlet starts. The
main MIDlet implements an ElementListener and receives updates about category
changes from the CategoryBar.


2. Prerequisites
-------------------------------------------------------------------------------

* Java ME basics
* LWUIT basics


3. Important files
-------------------------------------------------------------------------------

* `DemoMidlet.java`: The MIDlet entry point handling the CategoryBar
* `S40-with-themes.jar`: The Series 40 LWUIT distributable with theme support
* `S40-asha2013.jar`: The Nokia Asha software platform 1.0 LWUIT distributable with
   theme support

4. Compatibility
-------------------------------------------------------------------------------

Nokia Asha software platform and Series 40 phones with full touch
capabilities.

Developed with Nokia Asha SDK 1.0 and Netbeans 7.2.

4.1 Required Capabilities
-------------------------

CLDC 1.1, and MIDP 2.0.

4.2 Known Issues
----------------

None.


5. Building, installing, and running the application
-------------------------------------------------------------------------------

5.1 Preparations
----------------

Before opening the project, make sure Nokia SDK 2.0 for Java is installed and
added to NetBeans.

5.2 Building
--------------------

The project can be easily opened in NetBeans by selecting **Open Project** from
the File menu and selecting the application. Building is done by selecting
**Build main project**.

5.3 Nokia Asha phone
--------------------

You can install the application on a phone by transferring the JAR file
via Nokia Suite or over Bluetooth.


6. License
-------------------------------------------------------------------------------

See the license text file delivered with this project. The license file is also
available online at 
https://github.com/nokia-developer/lwuit-demos/blob/master/category-bar-demo/LICENSE.TXT


7. Related documentation
-------------------------------------------------------------------------------

LWUIT Developer's Library
* http://developer.nokia.com/Resources/Library/LWUIT/#!index.html

Nokia SDK for Java
* http://www.developer.nokia.com/Develop/Java/Tools/


8. Version history
-------------------------------------------------------------------------------

* 1.0 Initial release in Nokia Developer projects.
