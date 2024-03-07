package apackage.export.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class logoutActivity extends AppCompatActivity {

    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        img  = findViewById(R.id.imglogout);

        // Sự kiện khi ấn vào hình ảnh để đăng xuất
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện đăng xuất khi hình ảnh được nhấn
                FirebaseAuth.getInstance().signOut();
                // Chuyển về màn hình đăng nhập sau khi đăng xuất
                startActivity(new Intent(logoutActivity.this, LoginActivity.class));
                finish(); // Đóng màn hình hiện tại để ngăn người dùng quay lại màn hình đã đăng nhập
            }
        });
    }


}
