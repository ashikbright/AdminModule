package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class WorkerMgmt extends AppCompatActivity {
    private TextView btnLogin, errorMsg;
    private EditText editName, editEmail, editPhone, editPassword1, editPassword2,editaddress;
    private Spinner spinner;
    private Button btnRegisterUser;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_mgmt);

        mAuth = FirebaseAuth.getInstance();

        btnRegisterUser = findViewById(R.id.createAccount);
        editName = findViewById(R.id.name);
        spinner=findViewById(R.id.edwtypes);
        editEmail = findViewById(R.id.email);
        editPhone = findViewById(R.id.phone);
        editPassword1 = findViewById(R.id.password1);
        editPassword2 = findViewById(R.id.password2);
        progressBar = findViewById(R.id.register_progressBar);
        errorMsg = findViewById(R.id.errorMsg_register);
        editaddress=findViewById(R.id.address);

        Intent mIntent = getIntent();
        int selectedItem = mIntent.getIntExtra("itemSelected", 0);
        spinner.setSelection(selectedItem);


        btnRegisterUser.setOnClickListener(v -> registerUser());

    }


    private void registerUser() {
        String name = editName.getText().toString().trim();
        String workerType=spinner.getSelectedItem().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();
        String password1 = editPassword1.getText().toString().trim();
        String password2 = editPassword2.getText().toString().trim();
        String address=editaddress.getText().toString().trim();
        String isWorker="1";


        if (name.isEmpty()){
            editName.setError("Full name is required!");
            editName.requestFocus();
            return;
        }

        if (address.isEmpty()){
            editName.setError("Address is required!");
            editName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editEmail.setError("Email is required!");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid Email");
            editEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            editPhone.setError("Phone number is required!");
            editPhone.requestFocus();
            return;
        }
        if (phone.length() != 10){
            editPhone.setError("please enter a valid phone number");
            editPhone.requestFocus();
            return;
        }

        if (password1.isEmpty()){
            editPassword1.setError("Password is required!");
            editPassword1.requestFocus();
            return;
        }
        if(password1.length() < 6){
            editPassword1.setError("Password must be at least 6 character");
            editPassword1.requestFocus();
            return;
        }
        if (password2.isEmpty()){
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Confirm your password");
            editPassword2.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_error_bg));
            editPassword2.requestFocus();
            return;
        }
        if (!password1.equals(password2)){
            errorMsg.setText("Password does not match!");
            editPassword2.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_error_bg));
            editPassword2.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        editPassword2.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_bg));
        errorMsg.setVisibility(View.GONE);
        String password = password1;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Workers workers=new Workers(name,workerType,email,phone,isWorker,address);
                            FirebaseDatabase.getInstance().getReference("Workers")
                                    .child(spinner.getSelectedItem().toString())
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(workers).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(WorkerMgmt.this, "Worker has registered successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(WorkerMgmt.this, loginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(WorkerMgmt.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(WorkerMgmt.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                        }else{
                            Toast.makeText(WorkerMgmt.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(WorkerMgmt.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}