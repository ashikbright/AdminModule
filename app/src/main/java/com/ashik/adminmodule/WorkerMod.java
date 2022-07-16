package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class WorkerMod extends AppCompatActivity {
    Button addprofile;

    ImageButton imgBtnLabour, imgBtnMistri, imgBtnTiles, imgBtnPaint, imgBtnFurniture, imgBtnPlumber, imgBtnWelder, imgBtnElectrician;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_mod);
        addprofile=findViewById(R.id.createwprofile);

        imgBtnLabour=findViewById(R.id.imgbtn_labour);
        imgBtnMistri=findViewById(R.id.imgbtn_mistri);
        imgBtnTiles=findViewById(R.id.imgbtn_tiles);
        imgBtnPaint=findViewById(R.id.imgbtn_painter);
        imgBtnFurniture=findViewById(R.id.imgbtn_furniture);
        imgBtnPlumber=findViewById(R.id.imgbtn_plumber);
        imgBtnWelder=findViewById(R.id.imgbtn_plumber2);
        imgBtnElectrician=findViewById(R.id.imgbtn_plumber3);
       addprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(WorkerMod.this, WorkerMgmt.class);
               startActivity(intent);
           }
       });

        imgBtnLabour.setOnClickListener(v -> confirmOrder(0));

        imgBtnMistri.setOnClickListener(v -> confirmOrder(1));

        imgBtnTiles.setOnClickListener(v -> confirmOrder(2));

        imgBtnPaint.setOnClickListener(v -> confirmOrder(3));

        imgBtnFurniture.setOnClickListener(v -> confirmOrder(4));

        imgBtnPlumber.setOnClickListener(v -> confirmOrder(5));

        imgBtnWelder.setOnClickListener(v -> confirmOrder(6));

        imgBtnElectrician.setOnClickListener(v -> confirmOrder(7));

    }
    private  void confirmOrder(int selectedItem) {
        Intent intent = new Intent(WorkerMod.this, WorkerMgmt.class);
        intent.putExtra("itemSelected", selectedItem);
        startActivity(intent);
    }
}