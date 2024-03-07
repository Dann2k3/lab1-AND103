package apackage.export.test;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthManager {

    private static final String TAG = "PhoneAuthManager";

    private FirebaseAuth mAuth;
    private Activity mActivity;
    private String mVerificationId;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // Constructor
    public PhoneAuthManager(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        mActivity = activity;

        // Khởi tạo callbacks cho xác minh số điện thoại
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Xác minh tự động khi số điện thoại được xác minh
//                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // Xác minh thất bại, xử lý tại đây
                Toast.makeText(mActivity, "Xác minh số điện thoại thất bại" + e, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onVerificationFailed", e);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Mã xác minh đã được gửi, lưu trữ verificationId để sử dụng khi xác minh
                mVerificationId = verificationId;
            }
        };
    }

    // Hàm bắt đầu quá trình xác minh số điện thoại
    public void startPhoneVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(mActivity)
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Hàm xác minh số điện thoại với mã xác minh nhập vào
    public void verifyPhoneNumberWithCode( String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    // Hàm đăng nhập với PhoneAuthCredential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        Toast.makeText(mActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Activity.class, logoutActivity.class));
                        Log.d(TAG, "signInWithCredential:success");
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(mActivity, "Đăng nhập thất bại" + task.getException(), Toast.LENGTH_LONG).show();
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }
}
