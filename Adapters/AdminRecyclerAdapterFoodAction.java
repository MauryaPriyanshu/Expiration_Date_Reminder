package com.foodwastemanagement.Adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.foodwastemanagement.Model.Event;
import com.foodwastemanagement.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class AdminRecyclerAdapterFoodAction extends RecyclerView.Adapter<AdminRecyclerAdapterFoodAction.View_Holder>{

    List<Event> datalist;
    Context context;
    FirebaseFirestore myDB;
    public AdminRecyclerAdapterFoodAction(ArrayList<Event> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
        this.myDB=FirebaseFirestore.getInstance();
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_admin_food_action, parent,false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(final View_Holder holder, @SuppressLint("RecyclerView") final int i) {
        final String StrEventDate = datalist.get(i).geteDate();
        final String StrFoodTpe = datalist.get(i).geteType();
        final String StrEventTime = datalist.get(i).geteTime();
        final String StrEventCount = datalist.get(i).geteCount();
        final String StrMessage = datalist.get(i).geteMsg();
        final String EventID = datalist.get(i).geteId();
        final String EventStatus = datalist.get(i).geteStatus();


       holder.EventDate.setText(StrEventDate);
        holder.FoodType.setText(StrFoodTpe);
        holder.EventTime.setText(StrEventTime);
        holder.EventCount.setText(StrEventCount);
        holder.Message.setText(StrMessage);
        if(EventStatus.equals("W")){
            holder.EventStatus.setText("Pending");
        }else if(EventStatus.equals("R")){
            holder.EventStatus.setText("Reject");
        }else if(EventStatus.equals("A")){
            holder.EventStatus.setText("Approved");
        }else if(EventStatus.equals("AU")){
            holder.EventStatus.setText("Accepted");
        }
        else if(EventStatus.equals("C")){
            holder.EventStatus.setText("Confirm");
        }
        holder.BtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Accept Food Request");
                alertDialog.setMessage("Are you sure want to Accept!! ");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            if (EventStatus.equals("W") || EventStatus.equals("R") || EventStatus.equals("A")) {

                                myDB.collection("Event")
                                        .document(EventID).update("eStatus", "A")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                datalist.get(i).seteStatus("A");
                                                notifyItemChanged(i);
                                            }
                                        });
                            }
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });
        holder.BtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Reject Food Request");
                alertDialog.setMessage("Are you sure want to Reject!! ");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (EventStatus.equals("W") || EventStatus.equals("R") || EventStatus.equals("A")) {

                            myDB.collection("Event")
                                    .document(EventID).update("eStatus", "R")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            datalist.get(i).seteStatus("R");
                                            notifyItemChanged(i);
                                        }
                                    });
                        }
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        protected TextView EventDate,FoodType,EventTime,EventCount,Message,EventStatus;
        protected Button BtnAccept,BtnReject;
        LinearLayout Linear;




        public View_Holder(View view) {
            super(view);

            this.EventDate = (TextView) view.findViewById(R.id.name);
            this.FoodType = (TextView) view.findViewById(R.id.website);
            this.EventTime = (TextView) view.findViewById(R.id.address);
            this.EventCount = (TextView) view.findViewById(R.id.regno);
            this.Message = (TextView) view.findViewById(R.id.tv_msg);
            this.EventStatus = (TextView) view.findViewById(R.id.status);

            this.BtnAccept = (Button) view.findViewById(R.id.btn_accept);
            this.BtnReject = (Button) view.findViewById(R.id.btn_reject);

            this.Linear = (LinearLayout) view.findViewById(R.id.linearclick1);


        }
    }


}
