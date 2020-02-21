package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    public void studentMode(View view){
        this.finish();
        startActivity(new Intent(Mode.this, StudentMain.class));
    }

    public void professorMode(View view){
        this.finish();
        startActivity(new Intent(Mode.this, ProfMain.class));
    }
}
