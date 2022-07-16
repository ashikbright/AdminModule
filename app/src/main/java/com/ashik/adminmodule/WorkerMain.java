package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerMain extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Workers> list;
    DatabaseReference databaseReference;
    DispAdapter adapter;
    Button add,remove;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WorkerMain.this,ProfileFragment.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_main);
        recyclerView=findViewById(R.id.recyclerview);
        add=findViewById(R.id.btnadd);
        remove=findViewById(R.id.btnremove);
        databaseReference= FirebaseDatabase.getInstance().getReference("Workers");
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
                Intent intent=new Intent(WorkerMain.this,WorkerMod.class);
                startActivity(intent);
            }
        });
    }
}