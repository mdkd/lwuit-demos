LWUIT List Demo
===============

This application shows how to make different types of lists using LWUIT.

* The implicit list can be used for drill downs. The list closes after an item
  is selected.
* The implicit + action example shows how to use Commands with lists.
* The exclusive list example demonstrates how to make an exclusive selection.
  The existing selection is displayed as a highlighted radio button and new
  selection can be made by tapping the list.
* The exclusive + confirm list is a list where the selection can be changed
  until it is explicitly confirmed.
* The multiple list can be used for selecting multiple items.
* Truncated shows an implicit list where the list items are truncated to fit
  in one row.
* Wrapped shows an implicit list where the texts are wrapped to multiple lines.
* Thumbnails shows an implicit list with thumbnail icons for each item. 

The application is hosted in GitHub:
https://projects.developer.nokia.com/LWUIT_ListDemo

For more information on LWUIT, visit LWUIT Developer's Guide: 
http://developer.nokia.com/Resources/Library/LWUIT/#!index.html


1. Usage
-------------------------------------------------------------------------------

Select the type of list you want to see from the main menu. Press Back on the
example to return to main menu. Press Back on the main menu to exit the
application.

The exclusive + confirm and multiple list examples need explicit confirmation
after making changes to the list. You can confirm the changes by pressing Done
(default command on touch devices, on non-touch devices the command is
available in the options menu). If you press Back after making changes a
confirmation Dialog is shown. The changes are accepted if you select Yes, if
you select No the changes are abandoned.


2. Prerequisites
-------------------------------------------------------------------------------

* Java ME basics
* LWUIT basics


3. Important classes
-------------------------------------------------------------------------------

* `DemoMidlet.java` - The Main MIDlet class
* `ListView.java` - The base class for all list examples.
* `ImplicitListView.java` - Implicit list
* `ImplicitActionListView.java` - Implicit list + action
* `ExclusiveListView.java` - Exclusive list
* `ExclusiveConfirmListView.java` - Exclusive list + confirm
* `MultipleListView.java` - Multiple list
* `TruncatedListView.java` - Truncated list
* `WrappedListView.java` - Wrapped list
* `ThumbnailsListView.java` - Thumbnails list


3.1 Design considerations
-------------------------

The list examples are in the com.nokia.example.listdemo.lists package. All
examples extend the abstract ListView class, which provides the common
functionality such as displaying the selected items in a dialog after exiting
the example, and showing a confirmation query when example is exited.

ExclusiveConfirmListView shows how to use a custom ListCellRenderer, the
RadioButtonListCellRenderer is the renderer implementation.

WrappedListView shows how to use GenericListCellRenderer. The renderer is used
to simply delegate the drawing to a component.


4. Compatibility
-------------------------------------------------------------------------------

Nokia Asha software platform 1.0 or later and all Series 40 phones with CLDC 1.1,
MIDP 2.0. Max heap memory of the device needs to be at least 2 MB.

Tested on Nokia Asha 501, Nokia Asha 311, Nokia Asha 308, Nokia Asha 305,
Nokia Asha 303 and Nokia X3-02.

Developed with Nokia Asha SDK 1.0 and Netbeans 7.1.


4.1 Required capabilities
-------------------------

CLDC 1.1, MIDP 2.0


4.2 Known issues
----------------

* Sometimes a white screen is displayed after application startup.
* Occasionally the list component selects the next index when tapping a list
  item.


5. Building, installing, and running the application
-------------------------------------------------------------------------------

The example has been made with NetBeans 7.1 and Nokia SDK 2.0. The project can 
be easily opened in NetBeans by selecting **Open Project** from the File menu 
and selecting the application.

Before opening the project, make sure the Nokia SDK 2.0 is installed and added 
to NetBeans. Ensure that LWUIT for Series 40 library is added in the Libraries 
section. Building is done by selecting 'Build main project'.

Installing the application on a phone can be done by transferring the JAR file 
via Nokia Ovi Suite or via Bluetooth.

The application can also be run with Eclipse.


6. License
-------------------------------------------------------------------------------

See the license text file delivered with this project. The license file is 
also available online at 
https://projects.developer.nokia.com/LWUIT_ListDemo/browser/lwuit_listdemo/LICENSE.TXT


7. Related documentation
-------------------------------------------------------------------------------

List Demo
* https://projects.developer.nokia.com/LWUIT_ListDemo/wiki

Nokia SDK for Java
* http://www.developer.nokia.com/Develop/Java/Tools/


8. Version history
-------------------------------------------------------------------------------

* v0.3    Nokia Asha software platform 1.0 support added.
* v0.1    First release (at Nokia Developer projects only).
