package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TimerTask task = new TimerTask() { // Schedules a task
            @Override
            public void run() { // Runs when the timer completes
                ImageView img = (ImageView)findViewById(R.id.imgKiwiboot);
                Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
                img.startAnimation(aniFade);

                finish(); // Close the splash screen
                startActivity(new Intent(SplashScreen.this, Login.class));
                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        };
        Timer opening = new Timer();
        opening.schedule(task, 2000); // 5000ms is 5 seconds
    }
}
