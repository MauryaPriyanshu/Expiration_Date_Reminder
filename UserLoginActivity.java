package com.foodwastemanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.foodwastemanagement.Model.User_Accounts;
import com.foodwastemanagement.Utility.PrefManager;
public class UserLoginActivity extends AppCompatActivity {
    TextInputLayout ilayusername,ilaypassword;
    EditText username, password;
    Button btnRegistration;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    User_Accounts usrData;
    String userName, Password,Type,userDataString;
    PrefManager prefManager;
    Intent i;
    Toolbar toolbar;
    SharedPreferences settings;
    TextView LoginBtn, SignupBtn, Skip, forgetPassword,OtpForgetpasword;
    private final static String TAG = "Login User";
    FirebaseFirestore mydb;
    LinearLayout canclelayout, SignupLinear, LoginLinear,LinearAdminLogin;
    ProgressDialog pBar;
    TextView tvUserSignUp,tvResturentSignUp;
    Button btnResturentLoginActivity,btnAdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        tvUserSignUp=findViewById(R.id.tv_usersignup);
        //tvResturentSignUp=findViewById(R.id.tv_resturentsignup);
        btnResturentLoginActivity=findViewById(R.id.Resturent_login);
        btnAdminLogin=findViewById(R.id. Admin_login);
        prefManager = new PrefManager(UserLoginActivity.this);
        cd=new ConnectionDetector(this);
        pBar=new ProgressDialog(this);

        i = getIntent();
        initialise();
        tvUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Intent intent=new Intent(UserLoginActivity.this,UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnResturentLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Intent intent = new Intent(UserLoginActivity.this, ResturentLoginActivity.class);
//                    Log.d("#11", "value: username:: password and username  is empty");
//                    startActivity(intent);

            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().equals("")){
                    ilayusername.setError("Enter Username");
                    return;
                }

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {

                    pBar.setMessage("Forget Password in Progress...");
                    pBar.setCancelable(false);
                    pBar.show();
                    mydb = FirebaseFirestore.getInstance();
                    DocumentReference docRef = mydb.collection("UserReg").document(username.getText().toString());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    pBar.hide();
                                    Log.d("#00", " document exist " + document.getId().toString());
                                    Log.d("#00", "DocumentSnapshot data: " + document.getData());
                                    if (document.getData().get("uUsername").toString().equals(username.getText().toString())) {
                                        Log.d("#00", " the exist ");


                                   //     Intent forget = new Intent(UserLoginActivity.this, ActivityPhoneAuthForget.class);
                                   //     forget.putExtra("type", "forget");
                                        //    forget.putExtra("phone", username.getText().toString());
                                        // startActivity(forget);

                                      /*  String number = username.getText().toString().trim();
                                        String phoneNumber = "+91" + number;

                                        Intent intent = new Intent(ActivityLogin.this, VerifyPhoneActivity.class);
                                        intent.putExtra("phonenumber", phoneNumber);
                                        startActivity(intent);
*/

                                    } else {
                                        Log.d("#00", "Not exist");
                                        Toast.makeText(UserLoginActivity.this, "Number Not registered", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    pBar.hide();
                                    Log.d("#00", "No such document");
                                    Toast.makeText(UserLoginActivity.this, "Number Not registered", Toast.LENGTH_SHORT).show();


                                }


                            }else{
                                pBar.hide();
                            }
                        }
                    });

                }else{
                    showInternetAlert();
                }
            }
        });



        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget4 = new Intent(UserLoginActivity.this, AdminLoginActivity.class);
                startActivity(forget4);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginLinear.setVisibility(View.VISIBLE);
                SignupLinear.setVisibility(View.GONE);
                LoginBtn.setBackgroundColor(Color.RED);
                LoginBtn.setTextColor(Color.WHITE);
                SignupBtn.setBackgroundColor(Color.WHITE);
                SignupBtn.setTextColor(Color.RED);
                SignupBtn.setBackgroundResource(R.drawable.line_red);
                LoginLinear.startAnimation(AnimationUtils.loadAnimation(
                        UserLoginActivity.this, R.anim.left_to_right
                ));

            }
        });
        if(i.getStringExtra("type")!=null) {
            Type = i.getStringExtra("type");

            if (Type.equals("signin")) {
                SignupLinear.setVisibility(View.VISIBLE);
                LoginLinear.setVisibility(View.GONE);
                SignupBtn.setBackgroundColor(Color.RED);
                SignupBtn.setTextColor(Color.WHITE);
                LoginBtn.setBackgroundColor(Color.WHITE);
                LoginBtn.setTextColor(Color.RED);
                LoginBtn.setBackgroundResource(R.drawable.line_red);
                SignupLinear.startAnimation(AnimationUtils.loadAnimation(
                        UserLoginActivity.this, R.anim.flip_in_left
                ));
            }
        }

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Is_Valid_Person_Name(username);
            }
        });
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Is_Valid_Password(password);
            }
        });
    }

    public void Login(View v) {
        if (validateLogin()) {
            Log.d("food", "Validation Complete:");
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                settings = getSharedPreferences("Zone", Context.MODE_PRIVATE);
                pBar.setMessage("Login in Progress...");
                pBar.setCancelable(false);
                pBar.show();
                Log.d("#44", "uername " + username.getText().toString());
                Log.d("#44", "password " + password.getText().toString());
                mydb = FirebaseFirestore.getInstance();
                mydb.collection("UserReg")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        Log.d("#44", "uername P" + doc.getData().get("uUsername").toString());
                                        Log.d("#44", "password P" + doc.getData().get("uPassword").toString());
                                        if (doc.getData().get("uUsername").toString().equals(username.getText().toString()) && doc.getData().get("uPassword").toString().equals(password.getText().toString())) {

                                            prefManager.setUserId(username.getText().toString());
                                            pBar.hide();
                                            //  Intent i = new Intent(UserLoginActivity.this, MainActivity.class);
                                            //  startActivity(i);

                                            finish();



                                        }
                                        else{
                                            pBar.hide();
                                            Toast.makeText(getBaseContext(), "Invalid userName or Password", Toast.LENGTH_LONG).show();

                                        }

                                    }

                                } else {
                                    Log.d("", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
            else{
                showInternetAlert();
            }
        }
    }


    void initialise() {

        SignupLinear = (LinearLayout) findViewById(R.id.linearlayout_reg);
        LoginLinear = (LinearLayout) findViewById(R.id.linearlayout_login);
        LoginBtn = (TextView) findViewById(R.id.login_btn);
        SignupBtn = (TextView) findViewById(R.id.signup_btn);


        ilayusername = (TextInputLayout) findViewById(R.id.lay_username);
        ilaypassword = (TextInputLayout) findViewById(R.id.lay_password);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        forgetPassword = (TextView) findViewById(R.id.forget_password);

    }


    boolean validateLogin() {
        boolean result = true;

        if (username.getText().toString().equals("")&& username.getText().length()>=10) {
            Toast.makeText(getApplicationContext(), "Please enter your 10 digit Username", Toast.LENGTH_SHORT).show();
            ilayusername.setError("Username Required");

            result = false;

        }
        if (password.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
            ilaypassword.setError("Password Required");
            result = false;
        }

        return result;
    }

    public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            edt.setError("Enter Username as Mobile No.)");

        }
        if (edt.getText().toString().length() >=11) {
            edt.setError("Enter 10 digit mobile number)");

        }
    }
    public void Is_Valid_Password(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            edt.setError("Enter Password");
        }
    }
    public void Is_Valid_Sign_Number_Validation(EditText edt, TextInputLayout edtLayout) throws NumberFormatException {
        if (edt.getText().toString().length()<=0) {
            edtLayout.setError("Required Number Only");
        } else {
            edtLayout.setError(null);
            edtLayout.setErrorEnabled(false);

        }
    } // END OF Edittext validation



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


