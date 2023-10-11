package com.foodwastemanagement;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgotPassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText et_EmailAddress;
    private Button bt_Reset;
    private ImageButton bt_Backhome_reset;
    private ProgressBar progressBar_Reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initializeElements();
        clickListener();

    }

    private void initializeElements() {
        bt_Backhome_reset =  findViewById(R.id.bt_Backhome_reset);
        et_EmailAddress =  findViewById(R.id.et_Reset_Email);
        progressBar_Reset =  findViewById(R.id.progressBar_ResetEmail);
        progressBar_Reset.setVisibility(View.GONE);
        bt_Reset =  findViewById(R.id.bt_ResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void clickListener() {
        bt_Reset.setOnClickListener(v -> resetPassword());
        bt_Backhome_reset.setOnClickListener(v -> {

            Intent iReg=new Intent(ActivityForgotPassword.this, ActivityLogin.class);
            startActivity(iReg);
          //  Navigation.findNavController(root).navigate(R.id.action_forgotPassword_to_login);
        });
    }

    private void resetPassword() {
        String email = et_EmailAddress.getText().toString().trim();
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

        if(error){
            return;
        }

        progressBar_Reset.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ActivityForgotPassword.this,"Check your email to reset your password!", Toast.LENGTH_LONG).show();

                Intent iReg=new Intent(ActivityForgotPassword.this, ActivityLogin.class);
                startActivity(iReg);
            }
            else {
                Toast.makeText(ActivityForgotPassword.this,"Try again! Something wrong happened", Toast.LENGTH_LONG).show();
            }
        });
    }
}