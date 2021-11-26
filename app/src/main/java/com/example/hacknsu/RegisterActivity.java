package com.example.hacknsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mConfirmPassword, mBloodGroup;
    Button mRegisterBtn;
    TextView mLoginText;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar progressBar;
    FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        mFullName = findViewById(R.id.username);
        mEmail = findViewById(R.id.email);
        mBloodGroup = findViewById(R.id.bloodgroup);
        mPassword = findViewById(R.id.pass);
        mConfirmPassword = findViewById(R.id.cpass);
        mRegisterBtn = findViewById(R.id.btn_register);
        mLoginText = findViewById(R.id.loginText);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = fAuth.getCurrentUser();

                if (mFirebaseUser != null) {
                    Toast.makeText(RegisterActivity.this, "You Are Logged In", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    //startActivity(i);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please Log In", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String cpass = mConfirmPassword.getText().toString();

                if (!(email.isEmpty() && pass.isEmpty())) {
                    fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful , Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String uid = fAuth.getUid();
                                            String username = mFullName.getText().toString();
                                            String email = mEmail.getText().toString();
                                            String blood = mBloodGroup.getText().toString();
                                            String pass = mPassword.getText().toString();
                                            String cpass = mConfirmPassword.getText().toString();

                                            // login information stored into database ( name, email, password, confirm password and phone sign in  )
                                            UserHelperClass helperClass = new UserHelperClass(username, email, blood, pass, cpass, uid);
                                            reference.child(uid).setValue(helperClass);
                                            finish();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                                        }
                                    }
                                });

                            }
                        }
                    });

                } else if (TextUtils.isEmpty(cpass)) {
                    mConfirmPassword.setError("Enter your confirmation password");
                }

                if (!cpass.equals(pass)) {
                    Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Password match", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);

            }
        });
        //Register_Button Method Ends here


        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(mAuthStateListener);


    }
}

