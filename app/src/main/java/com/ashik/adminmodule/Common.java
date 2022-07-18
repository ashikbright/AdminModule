package com.ashik.adminmodule;

import android.net.Uri;

import com.google.firebase.firestore.auth.User;

public class Common {

    public static User CurrentUser;

    public static Uri userProfileImage;


    public static String getEmail(String email){

        return email;
    }

}