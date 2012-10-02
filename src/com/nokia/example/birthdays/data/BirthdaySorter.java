/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *
 * See LICENSE.TXT for license information.
 */
package com.nokia.example.birthdays.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Helper for sorting a Vector of birthdays in the order in which they
 * will occur (as observed from now).
 */
public class BirthdaySorter {
    
    private static final int BEFORE = -1;
    private static final int EQUAL = 0;
    private static final int AFTER = 1;
    
    // Helpers for date comparison operations
    private static final Calendar CAL1 = Calendar.getInstance();
    private static final Calendar CAL2 = Calendar.getInstance();
    private static final long NOW_MILLIS = new Date().getTime();
    private static final int CURRENT_YEAR =
        Calendar.getInstance().get(Calendar.YEAR);
            
    /**
     * Sort a Vector of birthdays in the order of upcoming birthdays.
     * 
     * This method will alter the given Vector directly,
     * so handle with care.
     * 
     * @param birthdays Vector of Birthday objects
     */
    public void sort(Vector birthdays) {        
        int count = birthdays.size();
        Birthday[] workspace = new Birthday[count];
        Birthday[] target = new Birthday[count];
        
        try {
            birthdays.copyInto(target);
        } catch (Exception e) {
            System.out.println("Could not copy birthdays: " + e.getMessage());
            for (int i = 0; i < birthdays.size(); i++) {
                System.out.println(i + ": " + birthdays.elementAt(i));
            }
            return;
        }

        mergeSortRecursive(target, workspace, 0, count - 1);
        
        // Rearrange elements in the original vector
        System.out.println("Sorted:");
        for (int i = 0; i < target.length; i++) {
            System.out.println(i + ". " + target[i] + " (" + target[i].getTimeUntilNextOccurrence() + ")");
            birthdays.setElementAt(target[i], i);
        }
    }
    
    /**
     * Recursively merge sort an array of Birthday objects.
     * 
     * Both the target and workspace arrays need to be initialized at the
     * correct length for the first call of this method.
     * 
     * For more information about merge sort, see the Wikipedia article:
     * http://en.wikipedia.org/wiki/Merge_sort
     * 
     * @param target Target array to contain the final result
     * @param workspace Working array
     * @param lowerBoundary Lowest index to consider
     * @param upperBoundary Highest index to consider
     */
    private void mergeSortRecursive(Birthday[] target,
            Birthday[] workspace, int lowerBoundary, int upperBoundary) {
        // Only one item in this range, consider it sorted
        if (lowerBoundary == upperBoundary) {
            return;
        }
        
        // Find midpoint between the boundaries
        int mid = (lowerBoundary + upperBoundary) / 2;
                
        // Sort the low half
        mergeSortRecursive(target, workspace, lowerBoundary, mid);     
        
        // Short the high half
        mergeSortRecursive(target, workspace, mid + 1, upperBoundary);
        
        // Merge the two halves
        merge(target, workspace, lowerBoundary, mid + 1, upperBoundary);
    }
    
    private void merge(Birthday[] target, Birthday[] workspace,
            int lowPointer, int highPointer, int upperBoundary) {
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

    /**
     * Compare a birth date to another, considering the current time of year.
     * 
     * Suppose it's now the 1st of August:
     * 1 2 3 4 5 6 7 8 9 10 11 12
     *               ^
     * For showing the upcoming birthdays list, we need to consider
     * whether the dates compared have already happened this year or not.
            
     * This means that if people had birthdays each month, their
     * display order, as viewed on August 1st, would be:
     * 8 9 10 11 12 1 2 3 4 5 6 7
     * 
     * @param date1 First date of comparison
     * @param date2 Second date of comparison
     * @return BEFORE if date1 should appear before date2; AFTER if date1 should appear after date2, EQUAL if it makes no matter
     */
    private int compare(Birthday date1, Birthday date2) {        
        /*
         * Disregard the year by comparing the dates as if they belong to
         * current year: here we're only interested in the month + day.
         */
        CAL1.setTime(date1.getBirthday());
        CAL1.set(Calendar.YEAR, CURRENT_YEAR);
        CAL2.setTime(date2.getBirthday());
        CAL2.set(Calendar.YEAR, CURRENT_YEAR);

        long time1 = CAL1.getTime().getTime();
        long time2 = CAL2.getTime().getTime();
        
        /*
         * If one of the two dates has already occurred this year and the 
         * other one hasn't, the next occurrence (from now) comes first.
         */ 
        if (time1 < NOW_MILLIS && time2 > NOW_MILLIS) {
            // time1 has also happened this year, needs to go after time2
            return AFTER;
        }
        else if (time1 > NOW_MILLIS && time2 < NOW_MILLIS) {
            return BEFORE;
        }
        
        /*
         * If we reached this point, if means time1 and time2 are both on the
         * same side of the year, and we can compare them normally.
         */
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
