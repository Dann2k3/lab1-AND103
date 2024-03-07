package apackage.export.test;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class testLogin extends Activity {

    private static final String TAG = "PhoneAuthManager";

    private FirebaseAuth mAuth;
    private Activity mActivity;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    // Constructor
    public testLogin(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        mActivity = activity;

        // Khởi tạo callbacks cho xác minh số điện thoại
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Xác minh tự động khi số điện thoại được xác minh
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // Xác minh thất bại, xử lý tại đây
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(mActivity, "Xác minh số điện thoại thất bại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Mã xác minh đã được gửi, lưu trữ verificationId để sử dụng khi xác minh
                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(mActivity, "Mã xác minh đã được gửi", Toast.LENGTH_SHORT).show();

                // Gọi hàm để xử lý việc nhập mã xác minh
                // Ví dụ: showInputDialogForVerificationCode(verificationId);
            }
        };
    }

    // Hàm bắt đầu quá trình xác minh số điện thoại
    public void startPhoneVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Hàm xác minh số điện thoại với mã xác minh nhập vào
    public void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    // Hàm đăng nhập với PhoneAuthCredential
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        Log.d(TAG, "signInWithCredential:success");
                        Toast.makeText(mActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = task.getResult().getUser();
                        // Đối với các xử lý sau khi đăng nhập thành công, bạn có thể thực hiện tại đây
                    } else {
                        // Đăng nhập thất bại
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(mActivity, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
