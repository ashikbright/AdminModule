package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashik.adminmodule.Common.Common;
import com.ashik.adminmodule.Models.Workers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkersDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Workers> list;
    DatabaseReference databaseReference;
    DispAdapter adapter;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_workers);
        recyclerView=findViewById(R.id.recyclerview);
        add=findViewById(R.id.btnadd);

        Intent mIntent = getIntent();
        int selectedItem = mIntent.getIntExtra("itemSelected", 0);
        String selectedItemString = Common.getSelectedWorkerType(selectedItem);

        databaseReference= FirebaseDatabase.getInstance().getReference("Workers").child(selectedItemString);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new DispAdapter(this,list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Workers workers=dataSnapshot.getValue(Workers.class);
                    list.add(workers);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WorkersDetails.this, WorkerRegistration.class);   //start activity workerRegistration
                startActivity(intent);
            }
        });


    }



}