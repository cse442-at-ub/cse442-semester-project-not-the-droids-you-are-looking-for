package com.example.kiwiboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateShortAnwserQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shrt_awnsr_question);
    }
    public void branch(View v){
        try {
            Toast.makeText(this.getApplicationContext(), "Question has been posted", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){}
        this.finish();
        startActivity(new Intent(CreateShortAnwserQuestionActivity.this, ProfMain.class));
    }


}