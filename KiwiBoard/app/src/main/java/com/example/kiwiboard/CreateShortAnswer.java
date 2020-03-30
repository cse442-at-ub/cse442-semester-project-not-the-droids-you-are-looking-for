package com.example.kiwiboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateShortAnswer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_short_answer);
    }
    public void switch_to_main(View view){
        this.finish();
        startActivity(new Intent(CreateShortAnswer.this, ProfMain.class));
    }


}