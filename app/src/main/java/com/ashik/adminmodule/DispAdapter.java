package com.ashik.adminmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashik.adminmodule.Models.Workers;

import java.util.ArrayList;

public class DispAdapter extends RecyclerView.Adapter<DispAdapter.MyViewHolder> {
   Context context;
   ArrayList<Workers> list;
   //DatabaseReference databaseReference;

//  Intent mIntent = getIntent();
//    int selectedItem = mIntent.getIntExtra("itemSelected", 0);
//    String selectedItemString = Common.getSelectedWorkerType(selectedItem);

    public DispAdapter(Context context, ArrayList<Workers> list) {
        this.context=context;
        this.list=list;
    }

    public DispAdapter(WorkerDetailsHome context, ArrayList<Workers> list) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.workerdisplay,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DispAdapter.MyViewHolder holder, int position) {
    Workers workers=list.get(position);
    holder.name.setText(workers.getName());
    holder.worktype.setText(workers.getWorkertype());
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
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,worktype,email,address;
      //  ImageView dltworker;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.wname);
            worktype=itemView.findViewById(R.id.wtype);
            email=itemView.findViewById(R.id.wemail);
            address=itemView.findViewById(R.id.waddress);
           // dltworker=itemView.findViewById(R.id.deleteworker);
        }
    }
}
