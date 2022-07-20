package com.ashik.adminmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ashik.adminmodule.Models.Order;
import com.ashik.adminmodule.ViewHolder.ListOrderRecyclerAdapter;
import com.ashik.adminmodule.ViewHolder.orderRecyclerAdapter;
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
    private ImageButton imageButton;
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public FirebaseDatabase database;
    public DatabaseReference order;
    public ListOrderRecyclerAdapter myAdapter;
    public ArrayList<Order> orderList;
    public String statusCode = "0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        imageButton = findViewById(R.id.btn_back);
        database = FirebaseDatabase.getInstance();
        order = database.getReference("Orders");

        recyclerView = findViewById(R.id.BookingsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderList = new ArrayList<>();
        myAdapter = new ListOrderRecyclerAdapter(this, orderList);
        recyclerView.setAdapter(myAdapter);


        imageButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 131:
                Log.d("selectedItem", "updating order...");
                showUpdateSpinner(item);
//                myAdapter.updateOrder(item.getGroupId());
                return true;
            case 132:
//                myAdapter.deleteClient(item.getGroupId());
                return true;
            default:  return super.onContextItemSelected(item);
        }
    }

    private void showUpdateSpinner(MenuItem item) {

        String[] order_status = {
                "Pending", "Accept Order", "Cancel Order"
        };

        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, R.layout.order_update_status_layout, order_status);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = new Spinner(ListOrders.this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        spinner.setPadding(3,3,3,3);
        spinner.setAdapter(dataAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(ListOrders.this);
        builder.setTitle("Update Order");
        builder.setView(spinner);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedStatus = spinner.getSelectedItem().toString();
                statusCode =  updateStatus(selectedStatus);
                myAdapter.updateOrder(item.getGroupId(), statusCode, userID);
                Log.d("selectedStatus " , "status: " + statusCode );

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("selectedItem", "operation cancelled.");
            }
        });

        builder.create().show();

    }

    private String updateStatus(String selectedStatus) {

        if (selectedStatus.equals("Pending")){
            statusCode = "0";
            Log.d("selectedStatus " , "status: " + statusCode );
        }
        else if(selectedStatus.equals("Accept Order")){
            statusCode = "1";
            Log.d("selectedStatus " , "status: " + statusCode );
        }
        else if(selectedStatus == "Cancel Order"){
            statusCode = "2";
            Log.d("selectedStatus " , "status: " + statusCode );
        }
        else {
            statusCode = "3";
            Log.d("selectedStatus " , "status: pending");
        }

        return statusCode;
    }
}