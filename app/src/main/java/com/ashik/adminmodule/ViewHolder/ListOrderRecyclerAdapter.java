package com.ashik.adminmodule.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashik.adminmodule.Common.Common;
import com.ashik.adminmodule.Models.Order;
import com.ashik.adminmodule.R;

import java.util.ArrayList;

public class ListOrderRecyclerAdapter extends RecyclerView.Adapter<ListOrderRecyclerAdapter.OrderViewHolder> {

    Context context;
    ArrayList<Order> orderList;

    public ListOrderRecyclerAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView txtOderID, txtWorkerType, txtDate, txtStatus, txtLocation;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOderID = itemView.findViewById(R.id.order_id);
            txtWorkerType = itemView.findViewById(R.id.oder_type);
            txtDate = itemView.findViewById(R.id.order_date);
            txtStatus = itemView.findViewById(R.id.order_status);
            txtLocation = itemView.findViewById(R.id.order_place);

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

}
