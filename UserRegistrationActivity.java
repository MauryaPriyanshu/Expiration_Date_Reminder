package com.foodwastemanagement;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.foodwastemanagement.Model.User_Accounts;
import com.foodwastemanagement.Utility.PrefManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout ilayUserNameReg, ilayUserPasswordReg, ilayConformPasswordReg, ilayFastNameReg, ilayLastNameReg,ilayAddressReg,ilayCityReg,ilayDobReg;
    EditText etUserNameReg, etPasswordReg, etConformPasswordReg, etFirstNameReg, etLastNameReg,etAddressReg, etCityReg,etDobReg;
    Button btnRegistration;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    Intent i;
    private final static String TAG = "Login User";
    FirebaseFirestore mydb;
    PrefManager prefManager;
    ProgressDialog pBar;
    ImageView img;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button save, Delete, Cancel, SaveEdit;
    LinearLayout linearLayoutAdd, linearLayoutEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
       // img=(ImageView) findViewById(R.id.calender);

       // img.setOnClickListener(this);
       // img.setOnClickListener(this);


        prefManager = new PrefManager(UserRegistrationActivity.this);
        initialise();
        cd=new ConnectionDetector(this);
        pBar=new ProgressDialog(this);

        i = getIntent();
        initialise();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration:
                if (validateRegistration()) {
                    Log.d("#00", "Validation in");
                    Log.d("#00", "Validation Complete:");
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        Log.d("#00", "inside Intenet present");
                        pBar.setMessage("Registration in Progress...");
                        pBar.setCancelable(false);
                        pBar.show();
                        mydb = FirebaseFirestore.getInstance();
                        DocumentReference docRef = mydb.collection("UserReg").document( etUserNameReg.getText().toString());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Log.d("#00", " document exist " + document.getId().toString());
                                        //   Log.d("#00", "DocumentSnapshot data: " + document.getData());
                                        DocumentSnapshot doc = task.getResult();
                                        if (doc.getData().get("uUsername").toString().equals(etUserNameReg.getText().toString())&& ("uPassword").toString().equals(etLastNameReg.getText().toString())) {
                                            Log.d("#00", " the exist ");
                                            pBar.hide();
                                            showAlert();

                                        } else {
                                            Log.d("#00", "Not exist");
                                            insertmethod();
                                        }
                                    }else {
                                        Log.d("#00", "No such document");
                                        insertmethod();
                                    }


                                }
                            }
                        });


                    }


                }
                else {
                    showInternetAlert();
                }
                break;
            case R.id.calender:
                if (v == img) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    etDobReg.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                break;
        }
    }}

    void initialise() {





        ilayUserNameReg = (TextInputLayout) findViewById(R.id.lay_user_name_reg);
        ilayUserPasswordReg = (TextInputLayout) findViewById(R.id.lay_password_reg);
        ilayConformPasswordReg = (TextInputLayout) findViewById(R.id.lay_conform_password_reg);
        ilayFastNameReg = (TextInputLayout) findViewById(R.id.lay_first_name_reg);
        ilayLastNameReg = (TextInputLayout) findViewById(R.id.lay_last_name_reg);
        ilayAddressReg = (TextInputLayout) findViewById(R.id.lay_address_reg);
        ilayCityReg = (TextInputLayout) findViewById(R.id.lay_city_reg);
        ilayDobReg = (TextInputLayout) findViewById(R.id.lay_dob_reg);


        etUserNameReg = (EditText) findViewById(R.id.user_name_reg);
        etPasswordReg = (EditText) findViewById(R.id.password_reg);
        etConformPasswordReg = (EditText) findViewById(R.id.conformPassword_reg);
        etFirstNameReg = (EditText) findViewById(R.id.first_name_reg);
        etLastNameReg = (EditText) findViewById(R.id.last_name_reg);
        etAddressReg = (EditText) findViewById(R.id.address_reg);
        etCityReg = (EditText) findViewById(R.id.city_reg);
        etDobReg = (EditText) findViewById(R.id.dob_reg);

        btnRegistration = (Button) findViewById(R.id.registration);
        btnRegistration.setOnClickListener(this);
        img=(ImageView) findViewById(R.id.calender);
        img.setOnClickListener(this);


        etUserNameReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Is_Valid_Sign_Number_Validation(etUserNameReg, ilayUserNameReg);

            }
        });


        etPasswordReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etPasswordReg.getText().toString().equals("")) {
                    ilayUserPasswordReg.setError("Password Required.");
                } else {
                    ilayUserPasswordReg.setError(null);
                    ilayUserPasswordReg.setErrorEnabled(false);
                }
            }
        });

        etConformPasswordReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etConformPasswordReg.getText().toString().equals("")) {
                    ilayConformPasswordReg.setError("Conform Password Required.");
                } else {
                    ilayConformPasswordReg.setError(null);
                    ilayConformPasswordReg.setErrorEnabled(false);
                }
            }
        });

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

        etLastNameReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etLastNameReg.getText().toString().equals("")) {
                    ilayLastNameReg.setError("Last Name Required.");
                } else if (!etLastNameReg.getText().toString().matches("[a-zA-Z ]+")) {
                    ilayLastNameReg.setError("Accept Alphabets Only.");
                } else {
                    ilayLastNameReg.setError(null);
                    ilayLastNameReg.setErrorEnabled(false);
                }

            }
        });

        /*address city*/

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

        etAddressReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etCityReg.getText().toString().equals("")) {
                    ilayCityReg.setError("City Required.");
                } else if (!etCityReg.getText().toString().matches("[a-zA-Z ]+")) {
                    ilayCityReg.setError("Accept Alphabets Only.");
                } else {
                    ilayCityReg.setError(null);
                    ilayCityReg.setErrorEnabled(false);
                }

            }
        });

        /*address city*/
        etDobReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Is_Valid_Sign_Number_Validation(etDobReg, ilayDobReg);

            }
        });




    }


    boolean validateRegistration() {
        boolean result = true;

        if (!etUserNameReg.getText().toString().equals("")) {
            Is_Valid_Sign_Number_Validation(etUserNameReg, ilayUserNameReg);
        } else {
            ilayUserNameReg.setError("Mobile No. as Username Required");
            result = false;
        }

        if (etPasswordReg.getText().toString().equals("")) {
            ilayUserPasswordReg.setError("Password Required.");
            result = false;

        } else {
            ilayUserPasswordReg.setError(null);
            ilayUserPasswordReg.setErrorEnabled(false);
        }

        if (etConformPasswordReg.getText().toString().equals("")) {
            ilayConformPasswordReg.setError("Conform Password Required.");
            result = false;
        } else if (!etPasswordReg.getText().toString().equals(etConformPasswordReg.getText().toString())) {
            ilayConformPasswordReg.setError("Password Not matched");
            etConformPasswordReg.requestFocus();
            result = false;

        } else {
            ilayConformPasswordReg.setError(null);
            ilayConformPasswordReg.setErrorEnabled(false);
        }

        if (etFirstNameReg.getText().toString().equals("")) {
            ilayFastNameReg.setError("First Name Required...");

        } else {
            ilayFastNameReg.setError(null);
            ilayFastNameReg.setErrorEnabled(false);
        }

        if (etLastNameReg.getText().toString().equals("")) {
            ilayLastNameReg.setError("Last Name Required...");

        } else {
            ilayLastNameReg.setError(null);
            ilayLastNameReg.setErrorEnabled(false);
        }

/*address city*/

        if (etAddressReg.getText().toString().equals("")) {
            ilayAddressReg.setError("Address  Required...");

        } else {
            ilayAddressReg.setError(null);
            ilayAddressReg.setErrorEnabled(false);
        }

        if (etCityReg.getText().toString().equals("")) {
            ilayCityReg.setError("City Name Required...");

        } else {
            ilayCityReg.setError(null);
            ilayCityReg.setErrorEnabled(false);
        }
        if (etDobReg.getText().toString().equals("")) {
            ilayDobReg.setError("Date of birth Required...");

        } else {
            ilayDobReg.setError(null);
            ilayDobReg.setErrorEnabled(false);
        }
        return result;
    }


    public void Is_Valid_Sign_Number_Validation(EditText edt, TextInputLayout edtLayout) throws NumberFormatException {
        if (edt.getText().toString().length()<=0) {
            edtLayout.setError("Required Number Only");
        } else {
            edtLayout.setError(null);
            edtLayout.setErrorEnabled(false);

        }
    } // END OF Edittext validation



    void insertmethod(){
        final Map<String, Object> user = new HashMap<>();
        user.put("uUsername", etUserNameReg.getText().toString());
        user.put("uPassword", etPasswordReg.getText().toString());
        user.put("uFirstName", etFirstNameReg.getText().toString());
        user.put("uLastName", etLastNameReg.getText().toString());
        user.put("uAccountid", "");
        user.put("uFatherHusband", "");
        user.put("uMaritalStatus", "");
        user.put("uGender", "");
        user.put("uDOB", etDobReg.getText().toString());
        user.put("uType", "");
        user.put("uPusername", "");
        user.put("uRefBy", "");
        user.put("uEmail", "");
        user.put("uMobile", "");
        user.put("uPasskey", "");
        user.put("uAddress", etAddressReg.getText().toString());
        user.put("uAddress_line2", "");
        user.put("uAddress_line3", "");
        user.put("uCity", etCityReg.getText().toString());
        user.put("uState", "");
        user.put("uCountry", "");
        user.put("uPincode", "");
        user.put("uDesignation", "");
        user.put("uPhone", "");
        user.put("uLandMark", "");
        user.put("uAuthtype", "");
        user.put("uImage", "");
        user.put("uStatus", "");
        user.put("uValidate", "");
        user.put("uValidateBy", "");
        user.put("uIp", "");
        user.put("uValidM", "");
        user.put("uValidE", "");
        user.put("uRegdate", "");
        user.put("OuAddress", "");
        user.put("OuAddress_line3", "");
        user.put("OuAddress_line2", "");
        user.put("OuLandMark", "");
        user.put("OuCity", "");
        user.put("OuState", "");
        user.put("Oulatitude", "");
        user.put("Oulongitude", "");
        user.put("OuPincode", "");
        user.put("uRegBy", "");
        user.put("cdate", "");
        user.put("mdate", "");
        user.put("mby", "");
        user.put("ulatitude", "");
        user.put("ulongitude", "");

        mydb = FirebaseFirestore.getInstance();
        mydb.collection("UserReg").document(etUserNameReg.getText().toString()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference user = mydb.collection("UserReg").document(etUserNameReg.getText().toString());
                        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    prefManager.setUserId(etUserNameReg.getText().toString());
                                    pBar.hide();
                                    Toast.makeText(getBaseContext(), "Registration Successfull", Toast.LENGTH_LONG).show();
                                   // Intent i = new Intent(UserRegistrationActivity.this, ActivityPhoneAuth.class);
                                  //  i.putExtra("type","reg");
                                  //  startActivity(i);
                                    finish();

                                }
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pBar.hide();
                                    }
                                });
                    }


                });
    }
    void showInternetAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Check Your Internet Connection!!")
                .setCancelable(false)
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Retry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

        // Creating dialog box
        AlertDialog alert = builder.create();
        // Setting the title manually
        alert.setTitle("No Internet");
        alert.show();
    }
    void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This Number is already Registered!!")
                .setCancelable(false)
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        // Creating dialog box
        AlertDialog alert = builder.create();
        // Setting the title manually
        alert.setTitle("Already Registered");
        alert.show();
    }


}


