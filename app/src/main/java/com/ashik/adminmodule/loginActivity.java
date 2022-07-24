package com.ashik.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private TextView register, forgot, errorMsg;
    private EditText editEmail, editPassword;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.registerUser);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progressBar);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgetPassword);
        errorMsg = findViewById(R.id.errorMsg);

        mAuth = FirebaseAuth.getInstance();
        Log.d("logInfo",mAuth.toString());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogIn();
            }
        });

    }

    private void userLogIn() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //redirect to user profile
                            checkUserAccessLevel();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(loginActivity.this, "Failed to login! Please check your credentials!!!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("logInfo",  "Error: "+ e.toString());
            }
        });


    }

    private void checkUserAccessLevel() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        Log.d("checkUser", myRef.toString());

//        user = mAuth.getCurrentUser();
        userID = mAuth.getCurrentUser().getUid();

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("isAdmin").exists()) {
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(loginActivity.this, "ADMIN", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    finish();
                }

                else if (snapshot.child("isWorker").exists()){
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginActivity.this, "Worker", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else if (snapshot.child("isUser").exists()){
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginActivity.this, "USER", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(loginActivity.this, "Something went wrong\n" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
