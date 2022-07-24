package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    ArrayList<Workers> workerList;
    DatabaseReference databaseReference;
    DisplayWorkerDetailsAdapter adapter;
    Button btnAdd;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_details);
        recyclerView = findViewById(R.id.workers_recycler_view);
        btnAdd = findViewById(R.id.btnadd);
        backButton = findViewById(R.id.btn_back_workers_registration);

        Intent mIntent = getIntent();
        int selectedItem = mIntent.getIntExtra("itemSelected", 0);
        String selectedItemString = Common.getSelectedWorkerType(selectedItem);

        databaseReference = FirebaseDatabase.getInstance().getReference("Workers").child(selectedItemString);
        workerList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DisplayWorkerDetailsAdapter(this, workerList);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Workers workers = dataSnapshot.getValue(Workers.class);
                    workerList.add(workers);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkersDetails.this, WorkerRegistration.class);   //start activity workerRegistration
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkersDetails.this, WorkerDetailsHome.class);   //start activity workerRegistration
                startActivity(intent);
                finish();
            }
        });


    }


}