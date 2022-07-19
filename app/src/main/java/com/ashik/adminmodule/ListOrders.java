package com.ashik.adminmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ashik.adminmodule.Models.Order;
import com.ashik.adminmodule.ViewHolder.ListOrderRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOrders extends AppCompatActivity {

    private String userID;
    private ImageView imageView;
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public FirebaseDatabase database;
    public DatabaseReference order;
    public ListOrderRecyclerAdapter myAdapter;
    public ArrayList<Order> orderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        Intent intent = getIntent();
        userID = intent.getStringExtra("workerID");

        imageView = findViewById(R.id.btn_back);
        database = FirebaseDatabase.getInstance();
        order = database.getReference("Orders");

        recyclerView = findViewById(R.id.BookingsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderList = new ArrayList<>();
        myAdapter = new ListOrderRecyclerAdapter(this, orderList);
        recyclerView.setAdapter(myAdapter);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOrders.this, MainActivity.class);
                intent.putExtra("openBookings", 2);
                startActivity(intent);
                finish();
            }
        });


        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading...");
        dialog.show();

        if (userID != null) {

            order.child(userID).child("orderRequests").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Order myOrder = dataSnapshot.getValue(Order.class);
                        orderList.add(myOrder);
                    }

                    sortOrders();
                    myAdapter.notifyDataSetChanged();
                    Log.d("orderData", "data received successfully");
                    Log.d("orderData", orderList.toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("orderData", "data failed");
                }
            });

        } else {
            Log.d("userId", "user ID is null");
        }

        Handler handler = new Handler();
        handler.postDelayed(dialog::dismiss, 300);

    }

    private void sortOrders() {
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getOrderId().compareToIgnoreCase(o2.getOrderId());
            }
        });

        Collections.reverse(orderList);
    }

}