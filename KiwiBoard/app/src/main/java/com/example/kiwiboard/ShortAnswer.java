package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ShortAnswer extends AppCompatActivity {
    private EditText answer_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_answer);

    }
    public void switch_to_main(View view){
        this.finish();
        startActivity(new Intent(ShortAnswer.this, StudentMain.class));
    }
    //Credit: https://stackoverflow.com/questions/5308200/clear-text-in-edittext-when-entered used to derive line 26
    public void clear_txt_answer(View view){
        answer_view = (EditText) findViewById(R.id.txt_answer);
        try { answer_view.getText().clear(); }
        catch (Exception e){}

    }
}
