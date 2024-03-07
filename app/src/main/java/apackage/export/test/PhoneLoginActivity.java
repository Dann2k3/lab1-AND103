package apackage.export.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText phoneNumberEditText, verificationCodeEditText;
    private Button sendCodeButton, verifyCodeButton;

    private FirebaseAuth mAuth;
    private String mVerificationId;

    private PhoneAuthManager phoneAuthManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        mAuth = FirebaseAuth.getInstance();

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        verifyCodeButton = findViewById(R.id.verifyCodeButton);

        phoneAuthManager = new PhoneAuthManager(this);

        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    phoneAuthManager.startPhoneVerification(phoneNumber);
                    Toast.makeText(PhoneLoginActivity.this, "Đã gửi mã tới: " + phoneNumber, Toast.LENGTH_SHORT).show();
                    Log.d("PhoneLoginActivity", "Phone Number: " + phoneNumber);
                } else {
                    Toast.makeText(PhoneLoginActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificationCode = verificationCodeEditText.getText().toString();
                if (!verificationCode.isEmpty()) {
//                    phoneAuthManager.verifyPhoneNumberWithCode(mVerificationId);

                    String email = "quayeuha@gmail.com";
                    String password = "1234567";

                    // Truyền vào Activity hiện tại (this) để có thể gọi startActivity
                    LoginActivity.loginUser(email, password, PhoneLoginActivity.this);

                } else {
                    Toast.makeText(PhoneLoginActivity.this, "Vui lòng nhập mã xác minh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
