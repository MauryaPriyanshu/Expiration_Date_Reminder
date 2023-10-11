package com.foodwastemanagement.Adapters;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodwastemanagement.Model.Event;
import com.foodwastemanagement.R;
import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserRecyclerAdapterFoodAction extends RecyclerView.Adapter<UserRecyclerAdapterFoodAction.View_Holder>{

    List<Event> datalist;
    Context context;
    FirebaseFirestore myDB;
    PrefManager pref;

    public UserRecyclerAdapterFoodAction(ArrayList<Event> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
        this.myDB=FirebaseFirestore.getInstance();
        this.pref= new PrefManager(context);
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_food_action, parent,false);
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
        final String EventRmark = datalist.get(i).geteRemark();
        final String EventUserID = datalist.get(i).geteUserID();
        final String EventResID = datalist.get(i).geteResID();
        final String EventImgID = datalist.get(i).geteImage();
        final String strEmpName = datalist.get(i).geteEmpName();
        final String strEmpMobile = datalist.get(i).geteEmpMobile();
        final String strEmpIDProof = datalist.get(i).geteEmpIDProof();
        final String strEmpMsg = datalist.get(i).geteEmpMsg();
        final String strNgoToken = datalist.get(i).geteNgoToken();
        final String strResToken = datalist.get(i).geteResToken();

        if(!EventImgID.equals("")){
            Glide.with(context)
                    .load(EventImgID)
                    .into(holder.imgEvent);
        }


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
            holder.BtnAccept.setText(" Waiting Confirm ");
            holder.BtnAccept.setEnabled(false);
        }
        else if(EventStatus.equals("C")){
            holder.EventStatus.setText("Confirm");
        }
        if(!EventUserID.equals("")){
            holder.EventAssignTo.setText(strEmpName+" ("+strEmpMobile+")");
            holder.EventEmpIDProof.setText(strEmpIDProof);
            holder.EventNgoToken.setText("NGO: "+strNgoToken);
           if( EventStatus.equals("C")) {
                holder.EventResToken.setText("RES: " + strResToken);
                holder.BtnAccept.setText("Confirm");
                holder.BtnAccept.setEnabled(false);
            }else{
               holder.EventResToken.setText("RES: " + "****");
           }
            holder.EventRemark.setText(strEmpMsg);
        }

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



        holder.BtnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Accept Food Request");
                alertDialog.setMessage("Are you sure want to Accept!! ");


                // set the custom layout
                View view=LayoutInflater.from(context).inflate(R.layout.custom_alert_food_confimr,null);
                EditText etEmpName=(EditText)view.findViewById(R.id.et_emp_name);
                EditText etEmpMobile=(EditText)view.findViewById(R.id.et_emp_mobile);
                EditText etEmpIDProof=(EditText)view.findViewById(R.id.et_empid_details);
                EditText etEmpMsg=(EditText)view.findViewById(R.id.et_emp_msg);

                alertDialog.setView(view);
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            if (EventStatus.equals("A")) {

                                if(etEmpName.getText().toString()!="" && etEmpIDProof.getText().toString()!="" && etEmpMobile.getText().toString()!=""){

                                    Random rand = new Random();
                                    String randumRes = String.format("%04d", rand.nextInt(5000));
                                    String randumNgo = String.format("%04d", rand.nextInt(5000));

                                    myDB.collection("Event")
                                            .document(EventID).update("eStatus", "AU","eUserID",pref.getUserId(),"eNgoToken",randumNgo,"eResToken",randumRes,"eEmpName",etEmpName.getText().toString(),"eEmpMobile",etEmpMobile.getText().toString(),"eEmpIDProof",etEmpIDProof.getText().toString(),"eMsg",etEmpMsg.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    datalist.get(i).seteStatus("AU");
                                                    notifyItemChanged(i);
                                                }
                                            });
                                }else{
                                    etEmpName.setError("Name Required");
                                    etEmpIDProof.setError("ID Proof Required");
                                    etEmpMobile.setError("Mobile Required");
                                }


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

        protected TextView EventDate,FoodType,EventTime,EventCount,Message,EventStatus,EventRemark,EventAssignTo,tvTitle,tvSubTitle,tvResMobile;
        protected TextView EventEmpIDProof,EventNgoToken,EventResToken;
        protected Button BtnAccept;
        LinearLayout Linear;
        ImageView imgEvent,imgResturent,imgResWhatsApp;
        public View_Holder(View view) {
            super(view);
            this.EventDate = (TextView) view.findViewById(R.id.name);
            this.FoodType = (TextView) view.findViewById(R.id.website);
            this.EventTime = (TextView) view.findViewById(R.id.address);
            this.EventCount = (TextView) view.findViewById(R.id.regno);
            this.Message = (TextView) view.findViewById(R.id.tv_msg);
            this.EventStatus = (TextView) view.findViewById(R.id.status);
            this.EventAssignTo = (TextView) view.findViewById(R.id.assign_to);
            this.EventRemark = (TextView) view.findViewById(R.id.remark);
            this.BtnAccept = (Button) view.findViewById(R.id.btn_accept);
            this.imgEvent= (ImageView) view.findViewById(R.id.img_event);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_res_title);
            this.tvSubTitle = (TextView) view.findViewById(R.id.tv_res_subtitle);
            this.tvResMobile = (TextView) view.findViewById(R.id.tv_res_mobile);
            this.imgResturent= (ImageView) view.findViewById(R.id.img_res);
            this.imgResWhatsApp= (ImageView) view.findViewById(R.id.img_res_whatsapp);
            this.EventEmpIDProof= (TextView) view.findViewById(R.id.tv_empid_detail);
            this.EventNgoToken= (TextView) view.findViewById(R.id.tv_ngo_token);
            this.EventResToken= (TextView) view.findViewById(R.id.tv_res_token);
            this.Linear = (LinearLayout) view.findViewById(R.id.linearclick1);


        }
    }


}
