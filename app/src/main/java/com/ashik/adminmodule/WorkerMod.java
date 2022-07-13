package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WorkerMod extends AppCompatActivity {
    Button addprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_mod);
        addprofile=findViewById(R.id.createwprofile);

       addprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(WorkerMod.this,WorkerMod.class);
               startActivity(intent);
           }
       });
    }
}