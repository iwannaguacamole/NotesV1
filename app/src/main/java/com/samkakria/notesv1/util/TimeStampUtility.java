package com.samkakria.notesv1.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeStampUtility {
    public static String getCurrentTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM   yyyy", Locale.getDefault());
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            return null;
        }
    }

}
