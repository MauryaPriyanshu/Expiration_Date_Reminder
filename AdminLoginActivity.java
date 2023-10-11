package com.foodwastemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
Button btnAdminLogin;


String UserName="admin";
String Password="admin123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        etUserName=(EditText) findViewById(R.id.username_admin);
        etUserName.setText(UserName);
        etPassword=(EditText) findViewById(R.id.password_admin);
        btnAdminLogin=(Button) findViewById(R.id.admin_login);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(etUserName.getText().toString().equals(UserName) && etPassword.getText().toString().equals(Password)){
                     Log.d("#11","if condition"+UserName+Password);
                     Intent i = new Intent(AdminLoginActivity.this, AdminDashboard.class);
                     startActivity(i);
           }
                else{

                Toast.makeText(getApplicationContext(), "Please enter Valid Cediential ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }






}