package com.marcapps.a2games;

import static java.lang.Thread.sleep;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.marcapps.a2games.Activity2048;
import com.marcapps.a2games.MenuActivity;
import com.marcapps.a2games.R;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView icon;
    AnimatorSet animation = new AnimatorSet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createAnimation();
        icon = findViewById(R.id.welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createAnimation();
        animation.start();
    }

    private void createAnimation() {
        ObjectAnimator tinyX = ObjectAnimator.ofFloat(icon, "scaleX", 0.01f);
        tinyX.setDuration(1);
        ObjectAnimator tinyY = ObjectAnimator.ofFloat(icon, "scaleY", 0.01f);
        tinyY.setDuration(1);
        ObjectAnimator x0 = ObjectAnimator.ofFloat(icon, "translationX",-300f);
        x0.setDuration(1);
        ObjectAnimator y0 = ObjectAnimator.ofFloat(icon, "translationY",-700);
        y0.setDuration(1);

        ObjectAnimator bigX = ObjectAnimator.ofFloat(icon, "scaleX", 1f);
        bigX.setDuration(1000);
        ObjectAnimator bigY = ObjectAnimator.ofFloat(icon, "scaleY", 1f);
        bigY.setDuration(1000);
        ObjectAnimator x1 = ObjectAnimator.ofFloat(icon, "translationX",0f);
        x1.setDuration(1000);
        ObjectAnimator y1 = ObjectAnimator.ofFloat(icon, "translationY",400f);
        y1.setDuration(700);
        ObjectAnimator y2 = ObjectAnimator.ofFloat(icon, "translationY",0f);
        y2.setDuration(300);

        animation.play(tinyY).with(tinyX);
        animation.play(x0).with(tinyY);
        animation.play(y0).with(x0);
        animation.play(bigX).after(y0);
        animation.play(bigY).with(bigX);
        animation.play(x1).with(bigY);
        animation.play(y1).with(x1);
        animation.play(y2).after(y1);
    }
}
