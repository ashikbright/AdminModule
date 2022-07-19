package com.ashik.adminmodule.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashik.adminmodule.Common.Common;
import com.ashik.adminmodule.Models.Order;
import com.ashik.adminmodule.Models.User;
import com.ashik.adminmodule.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOrderRecyclerAdapter extends RecyclerView.Adapter<ListOrderRecyclerAdapter.OrderViewHolder> {

    Context context;
    ArrayList<Order> orderList;
    ArrayList<Order> orderListBackup;
    private int orderCount = 0;
    DatabaseReference orderRef;


    public ListOrderRecyclerAdapter(Context context, ArrayList<Order> orderList, ArrayList<Order> orderListBackUp) {
        this.context = context;
        this.orderList = orderList;
        this.orderListBackup = orderListBackUp;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        public TextView txtOderID, txtWorkerType, txtDate, txtStatus, txtLocation;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOderID = itemView.findViewById(R.id.order_id);
            txtWorkerType = itemView.findViewById(R.id.oder_type);
            txtDate = itemView.findViewById(R.id.order_date);
            txtStatus = itemView.findViewById(R.id.order_status);
            txtLocation = itemView.findViewById(R.id.order_place);


            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(this.getAdapterPosition(), 131, 0, "Update");             //groupId, itemId, order, title
            menu.add(this.getAdapterPosition(), 132, 1, "Delete");
        }
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.my_orders_by_users_list_item_layout, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOderID.setText(order.getOrderId());
        holder.txtWorkerType.setText(order.getWorkerType());
        holder.txtDate.setText(order.getDate());
        holder.txtLocation.setText(order.getAddress());

        String status_code = order.getStatus();
        String status = Common.checkStatus(status_code);
        holder.txtStatus.setText(status);

    }

    @Override
    public int getItemCount() {
        return  orderList.size();
    }

    public void updateOrder(int position, String statusCode, String userID) {

        Order CurrentOrder = orderListBackup.get(position);
        CurrentOrder.setStatus(statusCode);
        Common.setCurrentOrderStatus(statusCode);

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userID).child("orderRequests");

        updateStatusInFirebase(statusCode, position);
        notifyDataSetChanged();
    }

    private void updateStatusInFirebase(String statusCode, int position) {

//        orderRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//                    orderCount =  (int) snapshot.getChildrenCount();
//                }
//                else{
//                    Log.d("ColumnExist", "Not found");
//                }
//                String orderText = Integer.toString(orderCount);
//                Log.d("ColumnExist", "order count: " + orderText);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.d("ColumnExist", "ERROR : " + error);
//            }
//        });
         position+= 1;
        DatabaseReference reference = orderRef.child(String.valueOf(position));
        reference.child("status").setValue(statusCode);

        Log.d("positionCheck", "position: " + position);


    }

    public void deleteClient(int position) {
        Order CurrentOrder = orderList.get(position);

    }


}
