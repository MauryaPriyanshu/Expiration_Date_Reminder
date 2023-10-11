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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUserProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";
    TextInputLayout ilayUserNameReg, ilayUserPasswordReg, ilayConformPasswordReg, ilayFastNameReg, ilayLastNameReg,ilayAddressReg,ilayCityReg,ilayDobReg;
    EditText etUserNameReg, etPasswordReg, etConformPasswordReg, etFirstNameReg, etLastNameReg,etAddressReg, etMobileReg,etDescription;
    Button btnRegistration;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ProgressDialog pBar;
    FirebaseFirestore myDB;
    PrefManager pref;
    CircleImageView imgResturent;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialise();
        cd=new ConnectionDetector(this);
        pBar=new ProgressDialog(this);
        pref = new PrefManager(ActivityUserProfile.this);
        myDB = FirebaseFirestore.getInstance();
        imgResturent=(CircleImageView)findViewById(R.id.img_user);
        DocumentReference docRef = myDB.collection("UserReg").document(pref.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot doc = task.getResult();
                    etUserNameReg.setText(doc.getData().get("uEmail").toString());
                    etFirstNameReg.setText(doc.getData().get("uFirstName").toString());
                    etLastNameReg.setText(doc.getData().get("uLastName").toString());
                    etAddressReg.setText(doc.getData().get("uAddress").toString());
                    etMobileReg.setText(doc.getData().get("uMobile").toString());
                    etDescription.setText(doc.getData().get("uAboutUs").toString());

                    if(!doc.getData().get("uImage").toString().equals("")){
                        Glide.with(ActivityUserProfile.this)
                                .load(doc.getData().get("uImage").toString())
                                .into(imgResturent);
                    }

                }

            }
        });

        imgResturent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iImage = new Intent(ActivityUserProfile.this, ActivityUserImg.class);
                startActivity(iImage);
            }
        });


        /*=============================edit code=======================*/
        btnRegistration = (Button) findViewById(R.id.registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("#00", " document exist+ " + pref.getUserId());
                pref.setUserLastName(etFirstNameReg.getText().toString());
                pref.setUserLastName(etLastNameReg.getText().toString());
                myDB.collection("UserReg").document(pref.getUserId()).update("uFirstName",etFirstNameReg.getText().toString(),"uLastName",etLastNameReg.getText().toString(),"uAddress",etAddressReg.getText().toString(),"uAboutUs",etDescription.getText().toString(),"uMobile",etMobileReg.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent i = new Intent(ActivityUserProfile.this, ActivityDashboard.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("111", "Error adding document", e);
                    }

                });

                Log.d("#00", "Updatee code");

            }
        });
    }

    void initialise() {
        ilayUserNameReg = (TextInputLayout) findViewById(R.id.lay_user_name_reg);
        ilayUserPasswordReg = (TextInputLayout) findViewById(R.id.lay_password_reg);
        ilayConformPasswordReg = (TextInputLayout) findViewById(R.id.lay_conform_password_reg);
        ilayFastNameReg = (TextInputLayout) findViewById(R.id.lay_first_name_reg);
        ilayLastNameReg = (TextInputLayout) findViewById(R.id.lay_last_name_reg);
        ilayAddressReg = (TextInputLayout) findViewById(R.id.lay_address_reg);
        etUserNameReg = (EditText) findViewById(R.id.user_name_reg);
        etPasswordReg = (EditText) findViewById(R.id.password_reg);
        etConformPasswordReg = (EditText) findViewById(R.id.conformPassword_reg);
        etFirstNameReg = (EditText) findViewById(R.id.first_name_reg);
        etLastNameReg = (EditText) findViewById(R.id.last_name_reg);
        etAddressReg = (EditText) findViewById(R.id.address_reg);
        etMobileReg = (EditText) findViewById(R.id.mobilno_reg);
        etDescription = (EditText) findViewById(R.id.description_reg);
        etFirstNameReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etFirstNameReg.getText().toString().equals("")) {
                    ilayFastNameReg.setError("First Name Required.");
                } else if (!etFirstNameReg.getText().toString().matches("[a-zA-Z ]+")) {
                    ilayFastNameReg.setError("Accept Alphabets Only.");
                } else {
                    ilayFastNameReg.setError(null);
                    ilayFastNameReg.setErrorEnabled(false);
                }
            }
        });
        etAddressReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etAddressReg.getText().toString().equals("")) {
                    ilayAddressReg.setError("Address Name Required.");
                } else if (!etAddressReg.getText().toString().matches("[a-zA-Z ]+")) {
                    ilayAddressReg.setError("Accept Alphabets Only.");
                } else {
                    ilayAddressReg.setError(null);
                    ilayAddressReg.setErrorEnabled(false);
                }

            }
        });
    }

    boolean validateRegistration() {
        boolean result = true;

        if (etFirstNameReg.getText().toString().equals("")) {
            ilayFastNameReg.setError("First Name Required...");

        } else {
            ilayFastNameReg.setError(null);
            ilayFastNameReg.setErrorEnabled(false);
        }
        if (etLastNameReg.getText().toString().equals("")) {
            ilayLastNameReg.setError("Resturent Name Required...");

        } else {
            ilayLastNameReg.setError(null);
            ilayLastNameReg.setErrorEnabled(false);
        }

        if (etAddressReg.getText().toString().equals("")) {
            ilayAddressReg.setError("Address  Required...");

        } else {
            ilayAddressReg.setError(null);
            ilayAddressReg.setErrorEnabled(false);
        }
        return result;
    }

}
