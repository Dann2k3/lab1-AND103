package apackage.export.test;

// RegisterActivity.java
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText registerEmail, registerPassword;
    private Button registerButton;
    private TextView goToLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.goToLogin);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Registration success
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            finish();
//                        } else {
//                            // Registration failed
//                            // Handle the failure, show an error message, etc.
//                        }
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công " + email, Toast.LENGTH_LONG).show();
                            // Bạn có thể mở một activity mới ở đây hoặc thực hiện hành động khác.
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                            finish();
                        } else {
                            Log.d("xxxxxxxxx", "Lỗi", task.getException());
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
