package com.example.kiwiboard;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
    }

    public void resetPWPassthrough(View view){
        this.finish();
        startActivity(new Intent(ForgotPassword.this, ResetPassword.class));
    }



}
