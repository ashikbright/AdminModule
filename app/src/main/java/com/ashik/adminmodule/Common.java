package com.ashik.adminmodule;

import android.net.Uri;

import com.google.firebase.firestore.auth.User;


public class Common {

    public static User CurrentUser;

    public static Uri userProfileImage;

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

    public static String getEmail(String email){

        return email;
    }

}