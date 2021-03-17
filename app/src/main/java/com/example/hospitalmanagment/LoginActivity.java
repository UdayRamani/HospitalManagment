package com.example.hospitalmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.example.hospitalmanagment.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    AppCompatEditText email;
    AppCompatButton signInBtn,ContinueOTP;
    LinearLayout SignUp;
    PinView pinViewOTPreRe;
    TextView otpText;
    FirebaseAuth auth;
    String CodeSent;
    String datamobiel;
    FirebaseFirestore db;
    public ProgressDialog mProgressDialog;
    List<User> userlist=new ArrayList<>();
    User user;
    public List<String> mobileNumberlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.mailRegister);
        pinViewOTPreRe = findViewById(R.id.pinViewOTPreRe);
        signInBtn = findViewById(R.id.signUp);
        SignUp = findViewById(R.id.lltext1);
        auth = FirebaseAuth.getInstance();
        ContinueOTP=findViewById(R.id.ContinueOTP);
        mProgressDialog = new ProgressDialog(getApplicationContext());
        db=FirebaseFirestore.getInstance();

        otpText = findViewById(R.id.otpText);



        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SendVerificationCode();
            }
        });
        ContinueOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=pinViewOTPreRe.getText().toString();
                getVerificationCode(code);
            }
        });
    }

    void Visibility() {
        email.setVisibility(View.GONE);
        pinViewOTPreRe.setVisibility(View.VISIBLE);
        otpText.setVisibility(View.VISIBLE);
        signInBtn.setVisibility(View.INVISIBLE);
        ContinueOTP.setVisibility(View.VISIBLE);
    }
    void getVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(LoginActivity.this, "Successfully"+""+user, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(LoginActivity.this, "Code Wrong", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    void SendVerificationCode() {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        datamobiel=documentSnapshot.getString("mobileNumber");
                        mobileNumberlist.add(datamobiel);
                        Log.d("", "DocumentSnapshot data: " + documentSnapshot.getData());
                        for (int i=0;i<mobileNumberlist.size();i++)
                        {
                            Log.d( "list of mobile Number ",mobileNumberlist.get(i));
                            String phone = email.getText().toString().trim();
                            String phoneNo="+" + "91" + "" +phone;

                            if (TextUtils.isEmpty(phone)) {
                                email.setError("PhoneNumber Is Required");
                            }
                            else if(phone.length()<10){
                                email.setError("MobileNumber should be 10 digit");
                            }else if(!phone.equals(datamobiel))
                            {
                                email.setError("User Does not exist on this mobile number");
                            }
                            else {
                                signInBtn.setEnabled(false);
                                int colorInt=getResources().getColor(R.color.grey);
                                email.setEnabled(false);
                                signInBtn.setBackgroundTintList(ColorStateList.valueOf(colorInt));

                                PhoneAuthOptions options =
                                        PhoneAuthOptions.newBuilder(auth)
                                                .setPhoneNumber(phoneNo)       // Phone number to verify
                                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                                .setActivity(LoginActivity.this)                 // Activity (for callback binding)
                                                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                                                .build();
                                PhoneAuthProvider.verifyPhoneNumber(options);
                            }
                        }
                    }
                }
            }
        });


    }
    public  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
       @Override
       public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
           super.onCodeSent(s, forceResendingToken);
           CodeSent = s;
       }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d(phoneAuthCredential.getSmsCode().toString(), "onVerificationCompleted: ");
            Visibility();
            String code=phoneAuthCredential.getSmsCode();
            if (phoneAuthCredential.getSmsCode()!=null)
            {
                getVerificationCode(code);
                pinViewOTPreRe.setText(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d(e.getMessage().toString(), "onVerificationFailed: ");
            Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    };

}
