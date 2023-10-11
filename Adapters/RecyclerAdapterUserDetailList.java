package com.foodwastemanagement.Adapters;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.foodwastemanagement.Model.User_Accounts;
import com.foodwastemanagement.R;
import com.foodwastemanagement.UserRegistrationActivity;

import java.util.List;
public class RecyclerAdapterUserDetailList extends RecyclerView.Adapter<RecyclerAdapterUserDetailList.View_Holder>{
    List<User_Accounts> datalist;
    Context context;
    public RecyclerAdapterUserDetailList(List<User_Accounts> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent , int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_item_nav_user, parent,false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(final View_Holder holder, final int i) {
        final String UserName = datalist.get(i).getUsername();
        final String UserPass = datalist.get(i).getPassword();
        final String UserMobile = datalist.get(i).getMobile();
        final String UserAddress= datalist.get(i).getAddress();
        final String UserID = datalist.get(i).getUsername();

        holder.Name.setText(UserName);
        holder.Password.setText(UserPass);
        holder.lyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent=new Intent(context,RobinFormActivity.class);
             //   context.startActivity(intent);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UserRegistrationActivity.class);
                intent.putExtra("name",UserName);
                intent.putExtra("type","edit");
                intent.putExtra("address",UserAddress);
                intent.putExtra("mobile",UserMobile);
                intent.putExtra("pass",UserPass);
                intent.putExtra("userid",UserID);
                Log.d("#33","userid"+UserID);
                context.startActivity(intent);
            }
        });
        holder.Edited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,UserRegistrationActivity.class);
                intent.putExtra("name",UserName);
                intent.putExtra("pass",UserPass);
                intent.putExtra("type","edit");
                intent.putExtra("address",UserAddress);
                intent.putExtra("mobile",UserMobile);
                intent.putExtra("userid",UserID);
                Log.d("#33","userid"+UserID);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {
        protected TextView Name,Password,Delete,Edited;
        LinearLayout Linear,lyUser;
        public View_Holder(View view) {
            super(view);
            this.Name = (TextView) view.findViewById(R.id.name);
            this.Password = (TextView) view.findViewById(R.id.pass);
            this.Linear = (LinearLayout) view.findViewById(R.id.linearclick1);
            this.lyUser = (LinearLayout) view.findViewById(R.id.clik_user);
            this.Delete = (TextView) view.findViewById(R.id.delete);
            this.Edited = (TextView) view.findViewById(R.id.edited);
        }
    }


}
