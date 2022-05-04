package lio.foody.yami;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    Coms coms;
    TextView usernameInput;
    EditText emailEt, passwordEt;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailEt = findViewById(R.id.usernameInput);
        passwordEt = findViewById(R.id.passwordInput);
        coms=new Coms(this);
        mAuth=FirebaseAuth.getInstance();
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        usernameInput=findViewById(R.id.usernameInput);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Logging in ...");
        progressDialog.setCanceledOnTouchOutside(false);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Login");

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mIntent2 = new Intent(getApplicationContext(),HomePage.class);
//                mIntent2.putExtra("keyname",usernameInput.getText().toString());
//                startActivity(mIntent2);
//            }
//        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check validity of email and password
                String email=emailEt.getText().toString();
                String pass=passwordEt.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEt.setError("Invalid email");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    passwordEt.setError("Password is required");
                    return;
                }
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email,pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                coms.message("Error Login",e.getMessage());
                            }
                        });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
            }
        });
    }
}