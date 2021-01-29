package com.add.cipherlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password, nickname;
    private Button registration;
    private FirebaseAuth mAuth;
    private DatabaseReference userName;
    private TextView signup;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.username);
        registration = findViewById(R.id.register_button);
        signup = findViewById(R.id.signup);

        userName = FirebaseDatabase.getInstance().getReference("Users");

        registration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String emailID = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String name = nickname.getText().toString().trim();

                if (emailID.isEmpty() && pass.isEmpty() && name.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.signup_layout), "Field are empty!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else if (emailID.isEmpty()) {

                    email.setError("Type in email!");
                    email.requestFocus();

                }  else if (name.isEmpty()) {

                    nickname.setError("Type in nickname!");
                    nickname.requestFocus();

                }else if (pass.isEmpty()) {

                    password.setError("Type in password!");
                    password.requestFocus();

                } else if (!(emailID.isEmpty() && pass.isEmpty())) {

                    mAuth.createUserWithEmailAndPassword(emailID, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                Snackbar snackbar = Snackbar.make(findViewById(R.id.signup_layout), "Registration failed: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT);
                                snackbar.show();

                            } else {

                                user = FirebaseAuth.getInstance().getCurrentUser();

                                userName.child(user.getUid()).child("name").setValue(nickname.getText().toString().trim());
                                userName.child(user.getUid()).child("score").setValue(0);

                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);

                            }

                        }
                    });

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.signup_layout), "Error!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });
    }
}