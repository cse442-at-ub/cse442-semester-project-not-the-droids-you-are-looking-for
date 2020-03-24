package com.example.kiwiboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateShortAnswerQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shrt_awnsr_question);
    }
    public void switch_to_main(View view){
        this.finish();
        startActivity(new Intent(CreateShortAnswerQuestionActivity.this, ProfMain.class));
    }


}