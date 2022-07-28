package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProjectActivity extends AppCompatActivity {

    private TextView TxtProjectID, TxtProjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        TxtProjectID = findViewById(R.id.project_id);
        TxtProjectName = findViewById(R.id.project_name);


        Intent mIntent = getIntent();
        String projectID = mIntent.getStringExtra("projectID");
        String projectName = mIntent.getStringExtra("projectName");

        TxtProjectID.setText(projectID);
        TxtProjectName.setText(projectName);

    }
}