package com.ashik.adminmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashik.adminmodule.Models.Workers;

import java.util.ArrayList;

public class DisplayWorkerDetailsAdapter extends RecyclerView.Adapter<DisplayWorkerDetailsAdapter.MyViewHolder> {
   Context context;
   ArrayList<Workers> workersList;
   //DatabaseReference databaseReference;

//  Intent mIntent = getIntent();
//    int selectedItem = mIntent.getIntExtra("itemSelected", 0);
//    String selectedItemString = Common.getSelectedWorkerType(selectedItem);

    public DisplayWorkerDetailsAdapter(Context context, ArrayList<Workers> workersList) {
        this.context=context;
        this.workersList = workersList;
    }

    public DisplayWorkerDetailsAdapter(WorkerDetailsHome context, ArrayList<Workers> workersList) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_worker_display, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayWorkerDetailsAdapter.MyViewHolder holder, int position) {
    Workers workers= workersList.get(position);
    holder.name.setText(workers.getName());
    holder.workerType.setText(workers.getWorkertype());
    holder.email.setText(workers.getEmail());
    holder.address.setText(workers.getAddress());
//    holder.dltworker.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FirebaseDatabase.getInstance().getReference("Workers").child("selectedItemString").child(getRef(position).getKey());
//        }
//    });
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, workerType, email, address;
         ImageView dltWorker;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.wname);
            workerType = itemView.findViewById(R.id.wtype);
            email = itemView.findViewById(R.id.wemail);
            address = itemView.findViewById(R.id.waddress);
            dltWorker = itemView.findViewById(R.id.deleteworker);

        }
    }
}