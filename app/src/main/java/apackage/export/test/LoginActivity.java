package apackage.export.test;

// LoginActivity.java
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private Button quenmkbutton;
    private Button OTP;
    private TextView goToRegister;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        goToRegister = findViewById(R.id.goToRegister);
        quenmkbutton= findViewById(R.id.quenmkbtn);
        OTP= findViewById(R.id.btnLoginWithOTP);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                loginUser(email,password,LoginActivity.this);
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        quenmkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               quenmk();
            }
        });
        OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, te.class));
                startActivity(new Intent(LoginActivity.this, PhoneLoginActivity.class));
                Log.d(" ", "onClick: ");
            }
        });
    }

//    private void loginUser() {
//        String email = loginEmail.getText().toString();
//        String password = loginPassword.getText().toString();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công " + email, Toast.LENGTH_LONG).show();
//                            // Bạn có thể mở một activity mới ở đây hoặc thực hiện hành động khác.
//                            startActivity(new Intent(LoginActivity.this, logoutActivity.class));
////                            finish();
//                        } else {
//                            Log.d("xxxxxxxxx", "Lỗi", task.getException());
//                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại " + task.getException(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }

    public static void loginUser(String email, String password, Context context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(context, "Đăng nhập thành công " , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, logoutActivity.class);
                            context.startActivity(intent);
                        } else {
                            Log.d("xxxxxxxxx", "Lỗi", task.getException());
                            Toast.makeText(context, "Đăng nhập thất bại " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void quenmk(){
        String email = loginEmail.getText().toString();
        String emailAddress = "quangdtph21898@fpt.edu.vn";
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this,"Đã gửi reset mật khẩu tới email : " + email,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Lỗi: " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

