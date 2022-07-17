package com.ashik.adminmodule.Common;

import android.net.Uri;
import android.util.Log;

import com.ashik.adminmodule.Models.User;

import java.util.ArrayList;

public class Common {

    public static User CurrentUser;

    public static Uri userProfileImage;

    public static int orderCount;

    public static String checkStatus(String status_code) {

        String st = "N/A";

        if (status_code.equals("0")) {
            st = "Order Placed";
        } else if (status_code.equals("1")) {
            st = "Pending";
        } else if (status_code.equals("2")) {
            st = "Accepted";
        } else if (status_code.equals("3")) {
            st = "cancelled";
        }

        Log.d("statusCheck", "conditions not met , status is: " + st);
        return st;

    }

    public static String getSelectedWorkerType(int selectedItem) {
        String selectedItemName = "";

        switch (selectedItem)
        {
            case 0:
                    selectedItemName  = "Labour";
                    break;
            case 1:
                selectedItemName  = "Mistri";
                break;
            case 2:
                selectedItemName  = "Tiles/Marble Mistri";
                break;
            case 3:
                selectedItemName  = "Painter";
                break;
            case 4:
                selectedItemName  = "Furniture Works";
                break;
            case 5:
                selectedItemName  = "Plumber";
                break;
            case 6:
                selectedItemName  = "Welder";
                break;
            case 7:
                selectedItemName  = "Electrician";
                break;
        }


        return selectedItemName;
    }
}


