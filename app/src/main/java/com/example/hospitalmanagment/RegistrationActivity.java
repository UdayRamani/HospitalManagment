package com.example.hospitalmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hospitalmanagment.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    AppCompatEditText userName,mobileNumber,email,password,code;
    AppCompatButton signUpBtn;
    LinearLayout SignIn;
    FirebaseAuth auth;
    String CodeSent;
    FirebaseFirestore db;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        code=findViewById(R.id.code);
        email=findViewById(R.id.mailRegister);
        userName=findViewById(R.id.userName);
        mobileNumber=findViewById(R.id.phoneNumber);
        password=findViewById(R.id.passwordRegister);
        signUpBtn=findViewById(R.id.signUp);
        SignIn=findViewById(R.id.lltext1);
        auth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        mProgressDialog = new ProgressDialog(getApplicationContext());

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailText=email.getText().toString().trim();
                final String userNameText=userName.getText().toString().trim();
                final String mobileNumberText=mobileNumber.getText().toString().trim();
                final String passwordText=password.getText().toString().trim();
                final String codeText=code.getText().toString().trim();
                if (TextUtils.isEmpty(codeText))
                {
                    code.setError("Code Is Required");
                }
                else if (TextUtils.isEmpty(userNameText))
                {
                    userName.setError("Name Is Required");
                }
                else if (TextUtils.isEmpty(mobileNumberText))
                {
                    mobileNumber.setError("Mobile Number Is Required");
                } else if (mobileNumberText.length()<10)
                {
                    mobileNumber.setError("Mobile Number must be 10 digit");
                }
                else if (TextUtils.isEmpty(emailText))
                {
                    email.setError("Email Is Required");
                }
                else if (TextUtils.isEmpty(passwordText))
                {
                    password.setError("Password Is Required");
                }else if (passwordText.length()<6)
                {
                    password.setError("Password should be 6 Character");
                }
                else
                {
                    Log.d("Databar: ",emailText+"password : "+passwordText+"code : "+codeText+"MobileNumber : "+mobileNumberText+"userName : "+userNameText);
                    auth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(RegistrationActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    Log.d("Datasdds: ",emailText+"password : "+passwordText+"code : "+codeText+"MobileNumber : "+mobileNumberText+"userName : "+userNameText);

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        User user = new User(codeText,userNameText,mobileNumberText,emailText,passwordText);
                                        Log.d("Datasuccess: ",emailText+"password : "+passwordText+"code : "+codeText+"MobileNumber : "+mobileNumberText+"userName : "+userNameText);
                                        Log.d( "onComplete: ",user.getCode()+user.getEmail()+user.getMobileNumber()+user.getPassword()+user.getName());
                                        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error adding document", e);

                                            }
                                        });

                                        Intent intent=new Intent(RegistrationActivity.this,Dashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                }
            }
        });
    }
}