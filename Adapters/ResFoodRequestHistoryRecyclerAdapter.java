package com.foodwastemanagement.Adapters;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodwastemanagement.Model.Event;
import com.foodwastemanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ResFoodRequestHistoryRecyclerAdapter extends RecyclerView.Adapter<ResFoodRequestHistoryRecyclerAdapter.View_Holder>{

    List<Event> datalist;
    Context context;
    FirebaseFirestore myDB;
    public ResFoodRequestHistoryRecyclerAdapter(ArrayList<Event> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
        this.myDB=FirebaseFirestore.getInstance();
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_res_food_action_history, parent,false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int i) {
       final String StrEventDate = datalist.get(i).geteDate();
        final String StrFoodTpe = datalist.get(i).geteType();
        final String StrEventTime = datalist.get(i).geteTime();
        final String StrEventCount = datalist.get(i).geteCount();
       final String StrMessage = datalist.get(i).geteMsg();
       final String EventID = datalist.get(i).geteId();
        final String EventStatus = datalist.get(i).geteStatus();
        final String EventUserID = datalist.get(i).geteUserID();
        final String EventResID = datalist.get(i).geteResID();
        final String EventImgID = datalist.get(i).geteImage();

        if(!EventImgID.equals("")){
            Glide.with(context)
                    .load(EventImgID)
                    .into(holder.imgEvent);
        }

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

        holder.EventDate.setText(StrEventDate);
        holder.FoodType.setText(StrFoodTpe);
        holder.EventTime.setText(StrEventTime);
        holder.EventCount.setText(StrEventCount);
        holder.Message.setText(StrMessage);
if(EventStatus.equals("AU")){
    holder.layRes.setVisibility(View.VISIBLE);
    if(!EventResID.equals("")){

        DocumentReference docRef = myDB.collection("ResturentReg").document(EventResID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();
                    // etUserNameReg.setText(doc.getData().get("uEmail").toString());
                    // etFirstNameReg.setText(doc.getData().get("uFirstName").toString());
                    holder.tvTitle.setText(doc.getData().get("uLastName").toString());
                    holder.tvSubTitle.setText(doc.getData().get("uAddress").toString());
                    holder.tvResMobile.setText(doc.getData().get("uMobile").toString());
                    if(!doc.getData().get("uImage").toString().equals("")){
                        Glide.with(context)
                                .load(doc.getData().get("uImage").toString())
                                .into(holder.imgResturent);
                    }


                    holder.imgResWhatsApp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+doc.getData().get("uMobile").toString() + "&text="+"Hi, Namaste..May i chat you"));
                            context.startActivity(intent);
                        }
                    });

                }

            }
        });
    }
    if(!EventUserID.equals("")){
        holder.layUser.setVisibility(View.VISIBLE);
        DocumentReference docRef = myDB.collection("UserReg").document(EventUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();
                    holder.tvUserTitle.setText(doc.getData().get("uLastName").toString());
                    holder.tvUserSubTitle.setText(doc.getData().get("uAddress").toString());
                    holder.tvUserMobile.setText(doc.getData().get("uMobile").toString());
                    if(!doc.getData().get("uImage").toString().equals("")){
                        Glide.with(context)
                                .load(doc.getData().get("uImage").toString())
                                .into(holder.imgUser);
                    }


                    holder.imgResWhatsApp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+91"+doc.getData().get("uMobile").toString() + "&text="+"Hi, Namaste..May i chat you"));
                            context.startActivity(intent);
                        }
                    });

                }

            }
        });
    }
}else{
    holder.layRes.setVisibility(View.GONE);
    holder.layUser.setVisibility(View.GONE);
}
    }
    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        protected TextView EventDate,FoodType,EventTime,EventCount,Message,EventStatus,tvTitle,tvSubTitle,tvResMobile,tvUserTitle,tvUserSubTitle,tvUserMobile;
        LinearLayout Linear;
        RelativeLayout layRes,layUser;
        ImageView imgEvent,imgResturent,imgResWhatsApp,imgUser,imgUserWhatsApp;

        public View_Holder(View view) {
            super(view);

            this.EventDate = (TextView) view.findViewById(R.id.name);
            this.FoodType = (TextView) view.findViewById(R.id.website);
            this.EventTime = (TextView) view.findViewById(R.id.address);
            this.EventCount = (TextView) view.findViewById(R.id.regno);
            this.Message = (TextView) view.findViewById(R.id.tv_msg);
            this.EventStatus = (TextView) view.findViewById(R.id.status);
            this.imgEvent= (ImageView) view.findViewById(R.id.img_event);
            this.Linear = (LinearLayout) view.findViewById(R.id.linearclick1);

            this.layRes= (RelativeLayout) view.findViewById(R.id.lay_res);
            this.layUser= (RelativeLayout) view.findViewById(R.id.lay_user);

            this.tvTitle = (TextView) view.findViewById(R.id.tv_res_title);
            this.tvSubTitle = (TextView) view.findViewById(R.id.tv_res_subtitle);
            this.tvResMobile = (TextView) view.findViewById(R.id.tv_res_mobile);
            this.imgResturent= (ImageView) view.findViewById(R.id.img_res);
            this.imgResWhatsApp= (ImageView) view.findViewById(R.id.img_res_whatsapp);


            this.tvUserTitle = (TextView) view.findViewById(R.id.tv_user_title);
            this.tvUserSubTitle = (TextView) view.findViewById(R.id.tv_user_subtitle);
            this.tvUserMobile = (TextView) view.findViewById(R.id.tv_user_mobile);
            this.imgUser= (ImageView) view.findViewById(R.id.img_user);
            this.imgUserWhatsApp= (ImageView) view.findViewById(R.id.img_user_whatsapp);



        }
    }


}
