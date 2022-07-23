package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ashik.adminmodule.Common.Common;
import com.ashik.adminmodule.Models.User;
import com.ashik.adminmodule.ViewHolder.orderRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class OrderFragment extends Fragment{

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public FirebaseDatabase database;
    public DatabaseReference order;
    public orderRecyclerAdapter myAdapter;
    public ArrayList<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order, container, false);

        database = FirebaseDatabase.getInstance();
        order = database.getReference("Orders");

        recyclerView = view.findViewById(R.id.BookingsRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        userList = new ArrayList<>();

        myAdapter = new orderRecyclerAdapter(getActivity(), userList);
        recyclerView.setAdapter(myAdapter);

        registerForContextMenu(recyclerView);


//        ProgressDialog dialog = new ProgressDialog(getActivity());
//        dialog.setTitle("Please Wait");
//        dialog.setMessage("Loading...");
//        dialog.show();


            order.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User currentUser = dataSnapshot.child("userInfo").getValue(User.class);
                        userList.add(currentUser);

                        if (currentUser != null) {
                            Common.CurrentUser = currentUser;
                        }

                    }

                    sortOrders();
                    myAdapter.notifyDataSetChanged();
                    Log.d("userData", "data received successfully");
                    Log.d("userData", userList.toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("userData", "data failed");
                }
            });


        myAdapter.setOnItemClickListener(new orderRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                User userInfo = userList.get(position);
                String userID =  userInfo.getUserID();

                Log.d("clickListener", "position: "+ position);
                Log.d("clickListener", "userID : "+ userID);

                Intent intent = new Intent(requireActivity(), ListOrders.class);
                intent.putExtra("workerID", userID);
                startActivity(intent);
            }
        });

//        Handler handler = new Handler();
//        handler.postDelayed(dialog::dismiss, 300);


        return view;
    }



    private void sortOrders() {
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 121:
                    myAdapter.callClient(item.getGroupId());
                    return true;
            case 122:
                myAdapter.emailClient(item.getGroupId());
                return true;
            default:  return super.onContextItemSelected(item);
        }

    }

}