/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nokia.example.birthdays.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 */
public class BirthdaySorter {

    private static final long NOW_MILLIS = new Date().getTime();
    private static final int NOW_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final int BEFORE = -1;
    private static final int EQUAL = 0;
    private static final int AFTER = 1;
    
    private static final Calendar CAL1 = Calendar.getInstance();
    private static final Calendar CAL2 = Calendar.getInstance();
            
    /**
     * Sort a vector of birthdays in the order of upcoming birthdays.
     * 
     * @param birthdays Vector of Birthday objects
     */
    public static void sort(Vector birthdays) {
        System.out.println("Sorting birthdays");
        mergeSort(birthdays);
    }
    
    private static void mergeSort(Vector items) {
        int count = items.size();
        Birthday[] workspace = new Birthday[count];
        Birthday[] target = new Birthday[count];
        items.copyInto(target);
                
        mergeSortRecursive(target, workspace, 0, count - 1);
        
        for (int i = 0; i < target.length; i++) {
            System.out.println(i + 1 + ": " + target[i]);
        }

    }
    
    private static void mergeSortRecursive(Birthday[] target, Birthday[] workspace, int lowerBoundary, int upperBoundary) {
        // Range is 1, no need to sort
        if (lowerBoundary == upperBoundary) {
            return;
        }
        
        // Find midpoint
        int mid = (lowerBoundary + upperBoundary) / 2;
                
        mergeSortRecursive(target, workspace, lowerBoundary, mid);     // Sort low half
        mergeSortRecursive(target, workspace, mid + 1, upperBoundary); // Short high half
        merge(target, workspace, lowerBoundary, mid + 1, upperBoundary);  // Merge the two
    }
    
    private static void merge(Birthday[] target, Birthday[] workspace, int lowPointer, int highPointer, int upperBoundary) {
        int i = 0;
        int lowerBoundary = lowPointer;
        int mid = highPointer - 1;
        int itemCount = upperBoundary - lowerBoundary + 1;
        
        while (lowPointer <= mid && highPointer <= upperBoundary) {
            if (compare(target[lowPointer], target[highPointer]) == BEFORE) {
                workspace[i++] = target[lowPointer++];
            } else {
                workspace[i++] = target[highPointer++];
            }
        }
            
        while (lowPointer <= mid) {
            workspace[i++] = target[lowPointer++];
        }

        while (highPointer <= upperBoundary) {
            workspace[i++] = target[highPointer++];
        }

        for (i = 0; i < itemCount; i++) {
            target[lowerBoundary + i] = workspace[i];
        }
    }
    
    private static int compare(Birthday date1, Birthday date2) {        
        // Disregard the year by assuming this year
        CAL1.setTime(date1.getBirthday());
        CAL1.set(Calendar.YEAR, NOW_YEAR);
        CAL2.setTime(date2.getBirthday());
        CAL2.set(Calendar.YEAR, NOW_YEAR);
        
        long time1 = CAL1.getTime().getTime();
        long time2 = CAL2.getTime().getTime();
        
        // Before or after today this year?
        long diff = time1 - time2;
        
        // If date1 < date2, return BEFORE (-1)
        if (diff < 0) {
            return BEFORE;
        }
        // If date2 > date1, return AFTER (1)
        else if (diff > 0) {
            return AFTER;
        }        
        return EQUAL;
    }
}
