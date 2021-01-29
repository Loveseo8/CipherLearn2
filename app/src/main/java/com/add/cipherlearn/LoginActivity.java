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

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button login;
    private FirebaseAuth mAuth;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        signin = findViewById(R.id.signin);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String userEmail = emailTextView.getText().toString().trim();
                String userPassword = passwordTextView.getText().toString().trim();

                if (userEmail.isEmpty() && userPassword.isEmpty()) {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.signin_layout), "Field are empty!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else if (userEmail.isEmpty()) {

                    emailTextView.setError("Type in email!");
                    emailTextView.requestFocus();

                } else if (userPassword.isEmpty()) {

                    passwordTextView.setError("Type in password!");
                    passwordTextView.requestFocus();

                } else if (!(userEmail.isEmpty() && userPassword.isEmpty())) {

                    mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                Snackbar snackbar = Snackbar.make(findViewById(R.id.signin_layout), "Log in failed: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT);
                                snackbar.show();

                            } else {

                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);

                            }

                        }
                    });

                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.signin_layout), "Error!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
    }
}