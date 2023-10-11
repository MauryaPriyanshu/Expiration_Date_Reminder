package com.foodwastemanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.foodwastemanagement.Utility.PasswordStrength;
import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistration extends AppCompatActivity {

    private Button bt_RegisterUser;
    private ImageButton bt_Backhome_Registration;
    private ProgressBar progressBar_Register;
    private EditText et_Name, et_Surname;
    private CountryCodePicker ccp_PhoneCode;
    private EditText et_Phone;
    private TextInputLayout tilEmail_Registration;
    private EditText et_EmailAddress;
    private LinearLayout ll_Password;
    private EditText et_Password;
    private TextView tv_passwordstrength_registration;
    private ProgressBar progressBar_passwordstrength_registration;
    private boolean googleRegistration;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore myDB;
    PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        myDB = FirebaseFirestore.getInstance();
        pref = new PrefManager(ActivityRegistration.this);
        initializeElements();
        clickListeners();

    }

    private void initializeElements() {
        bt_Backhome_Registration =  findViewById(R.id.bt_Backhome_Registration);
        et_Name = findViewById(R.id.et_Name_Registration);
        et_Surname = findViewById(R.id.et_Surname_Registration);

        ccp_PhoneCode = findViewById(R.id.ccp_PhoneCode_Registration);
        et_Phone =  findViewById(R.id.et_Phone_Registration);
        ccp_PhoneCode.registerCarrierNumberEditText(et_Phone);

        tilEmail_Registration = findViewById(R.id.tilEmail_Registration);
        et_EmailAddress = findViewById(R.id.et_Email_Registration);

        ll_Password  = findViewById(R.id.ll_Password);
        et_Password = findViewById(R.id.et_Password_Registration);

        progressBar_passwordstrength_registration = findViewById(R.id.progressBar_PasswordStrength_Registration);
        tv_passwordstrength_registration = findViewById(R.id.tv_PasswordStrength_Registration);

        progressBar_Register =  findViewById(R.id.progressBar_RegistrationUser);
        progressBar_Register.setVisibility(View.GONE);

        bt_RegisterUser =  findViewById(R.id.bt_RegistrationUser);

        verifyIsGoogle();
    }
    private void verifyIsGoogle() {

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null){
            googleRegistration = b.getBoolean("IsGoogle",false);
        }
        else{
            googleRegistration = false;
        }

        if(googleRegistration){
            tilEmail_Registration.setVisibility(View.GONE);
            ll_Password.setVisibility(View.GONE);
        }
    }
    private void clickListeners() {

        bt_Backhome_Registration.setOnClickListener(v -> {

            Intent iReg=new Intent(ActivityRegistration.this, ActivityLogin.class);
            startActivity(iReg);
        });

        bt_RegisterUser.setOnClickListener(v -> verifyData());

        et_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }
    private void verifyData(){

        String name = et_Name.getText().toString().trim();
        String surname = et_Surname.getText().toString().trim();
        String phone = ccp_PhoneCode.getFormattedFullNumber();
        String email = et_EmailAddress.getText().toString().trim();
        String password = et_Password.getText().toString().trim();
        boolean error = false;

        if(name.isEmpty()){
            et_Name.setError("Name is required");
            et_Name.requestFocus();
            error = true;
        }

        if(surname.isEmpty()){
            et_Surname.setError("Surname is required");
            et_Surname.requestFocus();
            error = true;
        }

        if(!ccp_PhoneCode.isValidFullNumber()){
            et_Phone.setError("Phone is required or is not valid");
            et_Phone.requestFocus();
            error = true;
        }

        if(!googleRegistration){
            if(email.isEmpty()){
                et_EmailAddress.setError("Email address is required");
                et_EmailAddress.requestFocus();
                error = true;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_EmailAddress.setError("Please provide a valid email address");
                et_EmailAddress.requestFocus();
                error = true;
            }

            PasswordStrength passwordStrength = PasswordStrength.calculate(password);
            if(password.isEmpty() || passwordStrength.getStrength() <= 1){
                et_Password.setError("Password strength error");
                et_Password.requestFocus();
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityRegistration.this);
                dialog.setTitle("Password strength error");
                String mensage = "Your password needs to:" +
                        "\n\tInclude both lower and upper case characters" +
                        "\n\tInclude at least one number and symbol" +
                        "\n\tBe at least 8 characters long";

                dialog.setMessage(mensage);
                dialog.setNegativeButton("Confirm", (dialogInterface, which) -> dialogInterface.dismiss());
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                error = true;
            }

        }
        else{
            email = firebaseAuth.getCurrentUser().getEmail();
        }

        if(error){
            return;
        }

        registerUser(name,surname,phone,email,password);
    }

    private void registerUser(String name, String surname, String phone, String email, String password) {
        progressBar_Register.setVisibility(View.VISIBLE);

        if(!googleRegistration){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(ActivityRegistration.this,"An email has been sent to activate your account!", Toast.LENGTH_LONG).show();
                            firebaseUser.sendEmailVerification();
                            registerInFirebase(name,surname,phone,email,password,firebaseUser.getUid());
                        }
                        else {
                            Toast.makeText(ActivityRegistration.this,"Failed to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBar_Register.setVisibility(View.GONE);
                            return;
                        }
                    });
        }
        else {
            firebaseUser = firebaseAuth.getCurrentUser();
            registerInFirebase(name,surname,phone,email,password,firebaseUser.getUid());
        }

    }
    private void registerInFirebase(String name, String surname, String phone, String email, String password, String userID) {

        final Map<String, Object> user = new HashMap<>();
        user.put("uUsername", userID);
        user.put("uPassword", password);
        user.put("uFirstName",name);
        user.put("uLastName", surname);
        user.put("uAboutUs", "");
        user.put("uFatherHusband", "");
        user.put("uMaritalStatus", "");
        user.put("uGender", "");
        user.put("uDOB", "");
        user.put("uType", "User");
        user.put("uPusername", "");
        user.put("uRefBy", "");
        user.put("uEmail", email);
        user.put("uMobile", phone);
        user.put("uPasskey", "");
        user.put("uAddress","");
        user.put("uAddress_line2", "");
        user.put("uAddress_line3", "");
        user.put("uCity", "");
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

        myDB.collection("UserReg").document(userID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference user = myDB.collection("UserReg").document(userID);
                        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();

                                    pref.setUserId(userID);
                                    pref.setUserEmail(doc.getString("uEmail"));
                                    pref.setUserFirstName(doc.getString("uFirstName"));
                                    pref.setUserLastName(doc.getString("uLastName"));
                                    pref.setUserType("User");
                                    progressBar_Register.setVisibility(View.GONE);
                                    Toast.makeText(ActivityRegistration.this,"User has ben registered successfully!", Toast.LENGTH_LONG).show();
                                     Intent i = new Intent(ActivityRegistration.this, ActivityDashboard.class);
                                     startActivity(i);
                                    finish();

                                }
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar_Register.setVisibility(View.GONE);
                                        Toast.makeText(ActivityRegistration.this,"Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }


                });

    }
    private void calculatePasswordStrength(String str) {
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        progressBar_passwordstrength_registration.setProgressTintList(ColorStateList.valueOf(passwordStrength.getColor()));
        progressBar_passwordstrength_registration.setProgress(passwordStrength.getStrength());
        tv_passwordstrength_registration.setText(passwordStrength.getMsg());
    }
}


