package com.foodwastemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.foodwastemanagement.Utility.PrefManager;

public class ActivityChangePassword extends AppCompatActivity {
    TextInputLayout lyPassword,lyConfirmPassword;
    EditText etCurrentPassword,etPassword,etConfirmPassword;
    Button btn_Submit;
    ProgressDialog pBar;
    private static final String TAG = "ActivityEditProfile";
    FirebaseFirestore myDB;
    PrefManager pref;

    FirebaseUser userAuth;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        lyPassword=(TextInputLayout) findViewById(R.id.lay_password);
        lyConfirmPassword=(TextInputLayout) findViewById(R.id.lay_confirm_password);
        etCurrentPassword=(EditText) findViewById(R.id.et_current_password);
        etPassword=(EditText) findViewById(R.id.et_password);
        etConfirmPassword=(EditText) findViewById(R.id.et_confirm_password);
        btn_Submit=(Button) findViewById(R.id.btn_submit);
        pBar=new ProgressDialog(this);

        pref = new PrefManager(this);
        myDB = FirebaseFirestore.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        mAuth=FirebaseAuth.getInstance();


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPassword.getText().toString().equals("")) {
                    lyPassword.setError("Password Required.");
                } else {
                    lyPassword.setError(null);
                    lyPassword.setErrorEnabled(false);
                }
            }
        });

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etConfirmPassword.getText().toString().equals("")) {
                    lyConfirmPassword.setError("Conform Password Required.");

                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                    lyConfirmPassword.setError("Password Not matched");
                    etConfirmPassword.requestFocus();


                } else {
                    lyConfirmPassword.setError(null);
                    lyConfirmPassword.setErrorEnabled(false);
                }
              
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pBar.setMessage("Forget Password in Progress...");
                pBar.setCancelable(false);
                pBar.show();

                mAuth.signInWithEmailAndPassword(pref.getUserEmail(), etCurrentPassword.getText().toString())
                        .addOnCompleteListener(ActivityChangePassword.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Log.d(TAG, "##111 signInWithEmail:success");
                                  //  FirebaseUser user = mAuth.getCurrentUser();

                                    myDB.collection("UserReg").document(pref.getUserId()).update("uPassword",etPassword.getText().toString())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Log.d("##111", "Passwors S1");

                                                    String newPassword = etPassword.getText().toString();
                                                    userAuth.updatePassword(newPassword)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.d("##111", "Passwors S2");
                                                                        pBar.hide();
                                                                        Intent idash = new Intent(ActivityChangePassword.this, ActivityDashboard.class);
                                                                        startActivity(idash);

                                                                    }
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("##111", "Error Password", e);
                                                            pBar.hide();
                                                        }
                                                    });


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("##111", "Error adding document", e);
                                            pBar.hide();
                                        }

                                    });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG, "##111 signInWithEmail:failure", task.getException());

                                }
                            }
                        });



            }
        });

    }


}
