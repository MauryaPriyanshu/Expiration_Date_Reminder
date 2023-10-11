package com.foodwastemanagement;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.foodwastemanagement.Utility.InternalStorage;
import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActivityLogin extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private EditText et_EmailAddress,et_Password;
    private CheckBox cb_Remeber;
    private Button bt_Login, bt_Login_Google, bt_Register;
    private ProgressBar progressBar_Login;
    private TextView tv_Forgot_Password;
    Button btnResturentLoginActivity,btnAdminLogin;
    private final boolean autoLogin = true;
    PrefManager pref;
    FirebaseFirestore myDB;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = new PrefManager(ActivityLogin.this);
        myDB = FirebaseFirestore.getInstance();
        checkPermissions();
        initializeElements();
        clickListener();
        getRemeberData();
    }

    private void initializeElements() {
        et_EmailAddress =  findViewById(R.id.etEmail_Login);
        et_Password =  findViewById(R.id.etPassword_Login);
        cb_Remeber = findViewById(R.id.cb_Remeber);
        bt_Login =  findViewById(R.id.bt_Login);
        progressBar_Login =  findViewById(R.id.progressBar_Login);
        progressBar_Login.setVisibility(View.GONE);
        tv_Forgot_Password =  findViewById(R.id.tv_Forgot_Password);
        bt_Login_Google =  findViewById(R.id.bt_Login_Google);
        bt_Register =  findViewById(R.id.bt_Register);
        btnResturentLoginActivity=findViewById(R.id.Resturent_login);
        btnAdminLogin=findViewById(R.id.Admin_login);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void clickListener() {
        bt_Login.setOnClickListener(v -> verifyData());
        bt_Login_Google.setOnClickListener(v -> loginWithGoogle());
        bt_Register.setOnClickListener(v -> {

            Intent ii=new Intent(ActivityLogin.this, ActivityRegistration.class);
            ii.putExtra("IsGoogle", false);
            startActivity(ii);

           // Navigation.findNavController(root).navigate(R.id.action_login_to_registration, bundle);
        });

        tv_Forgot_Password.setOnClickListener(v -> {

            Intent iForget=new Intent(ActivityLogin.this, ActivityForgotPassword.class);
            startActivity(iForget);
           // Navigation.findNavController(root).navigate(R.id.action_login_to_forgotPassword);
        });

        btnResturentLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(ActivityLogin.this, ResturentLoginActivity.class);
//                Log.d("#11", "value: username:: password and username  is empty");
//                startActivity(intent);

            }
        });

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget4 = new Intent(ActivityLogin.this, AdminLoginActivity.class);
                startActivity(forget4);
            }
        });
    }

    private void loginWithGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(ActivityLogin.this,googleSignInOptions);

        Intent intent = googleSignInClient.getSignInIntent();

        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed,
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(ActivityLogin.this, task -> {
            if (task.isSuccessful()) {
                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                if (isNewUser) {
                    Intent ii=new Intent(ActivityLogin.this, ActivityRegistration.class);
                    ii.putExtra("IsGoogle", true);
                    startActivity(ii);
                   // Navigation.findNavController(getView()).navigate(R.id.action_login_to_registration, bundle);
                } else {
                    verifyTypeUser(firebaseAuth.getCurrentUser().getUid());
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "signInWithCredential:failure", task.getException());
            }
        });
    }

    private void verifyData(){
        String email = et_EmailAddress.getText().toString().trim();
        String password = et_Password.getText().toString().trim();
        boolean error = false;

        if(email.isEmpty()){
            et_EmailAddress.setError("Email Address is required");
            et_EmailAddress.requestFocus();
            error = true;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_EmailAddress.setError("Please provide valid Email Address");
            et_EmailAddress.requestFocus();
            error = true;
        }

        if(password.isEmpty()){
            et_Password.setError("Password is required");
            et_Password.requestFocus();
            error = true;
        }

        if(error){
            return;
        }

        progressBar_Login.setVisibility(View.VISIBLE);

        login(email,password);
    }

    private void login(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                bt_Login.setEnabled(false);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(cb_Remeber.isChecked()){
                    saveRemeberData(email,password);
                }
                else{
                    saveRemeberData("","");
                }

                if(user.isEmailVerified()){
                    progressBar_Login.setVisibility(View.GONE);
                    verifyTypeUser(user.getUid());
                }
                else{
                    user.sendEmailVerification();
                    Toast.makeText(ActivityLogin.this,"Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    bt_Login.setEnabled(true);
                    progressBar_Login.setVisibility(View.GONE);
                }
            }
            else {
                Toast.makeText(ActivityLogin.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                bt_Login.setEnabled(true);
                progressBar_Login.setVisibility(View.GONE);
            }
        });
    }

    private void verifyTypeUser(String userId){


        DocumentReference user = myDB.collection("UserReg").document(userId);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    pref.setUserId(userId);
                    pref.setUserType("User");
                    pref.setUserEmail(doc.getString("uEmail"));
                    pref.setUserFirstName(doc.getString("uFirstName"));
                    pref.setUserLastName(doc.getString("uLastName"));

                    progressBar_Login.setVisibility(View.GONE);
                    Toast.makeText(ActivityLogin.this,"User has been Login successfully!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ActivityLogin.this, ActivityDashboard.class);
                    startActivity(i);
                    finish();

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar_Login.setVisibility(View.GONE);
                        Toast.makeText(ActivityLogin.this,"Failed to Login! Try again!", Toast.LENGTH_LONG).show();
                    }
                });



    }

    private void saveRemeberData(String email, String password){
        try {
            InternalStorage.writeObject(ActivityLogin.this, "Email", email);
            InternalStorage.writeObject(ActivityLogin.this, "Password", password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getRemeberData() {
        try {
            String email = (String) InternalStorage.readObject(ActivityLogin.this, "Email");
            String password = (String) InternalStorage.readObject(ActivityLogin.this, "Password");

            if(!email.isEmpty() && !password.isEmpty()){
                et_EmailAddress.setText(email);
                et_Password.setText(password);
                cb_Remeber.setChecked(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(ActivityLogin.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(ActivityLogin.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 124);
            return false;
        }
        return true;
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 124: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  // permissions granted.
                    Log.e("Permissions Garated", "All"); // Now you call here what ever you want :)
                } else {
                    String perStr = "";
                    for (String per : permissions) {
                        perStr += "\n" + per;
                    }   // permissions list of don't granted permission
                    Log.e("Permissions Denied", perStr);
                }
                return;
            }
        }
    }

}


