package com.marcapps.a2games;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marcapps.a2games.gameClasses.Board2048;

public class Activity2048 extends AppCompatActivity {

    public static int[][] viewIds = new int[][]{
            {R.id.b00, R.id.b01, R.id.b02, R.id.b03},
            {R.id.b10, R.id.b11, R.id.b12, R.id.b13},
            {R.id.b20, R.id.b21, R.id.b22, R.id.b23},
            {R.id.b30, R.id.b31, R.id.b32, R.id.b33},
    };
    public static TextView[][] views = new TextView[4][4];

    public static Board2048 board = new Board2048();
    public static TextView gameOver;
    public static Animation bobAnimation;


    //OnCreate


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_2048);

        gameOver = findViewById(R.id.gameover);
        gameOver.setVisibility(View.INVISIBLE);

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                views[x][y] = findViewById(viewIds[x][y]);
            }
        }
        board = new Board2048();

        bobAnimation = AnimationUtils.loadAnimation(Activity2048.this,R.anim.appear_bounce);

        board.newBlock();
        board.newBlock();

        findViewById(R.id.grid2048).setOnTouchListener(new OnSwipeTouchListener(Activity2048.this) {
            public void onSwipeTop() {
                board.moveUp();
            }
            public void onSwipeRight() {
                board.moveRight();
            }
            public void onSwipeLeft() {
                board.moveLeft();
            }
            public void onSwipeBottom() {
                board.moveDown();
            }

        });
        GridLayout layout = findViewById(R.id.grid2048);
    }

    //Window


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    //Methods


    public static void updateBlock(int x, int y) {
        int value = board.blocks[x][y];
        if (board.isVoid(x,y)) {
            views[x][y].setText("");
            views[x][y].setBackgroundResource(R.drawable.block0);
            views[x][y].clearAnimation();
        } else {
            views[x][y].setText(String.valueOf((int) Math.pow(2, value)));
            views[x][y].setBackgroundResource(getBlockColor(value));
            views[x][y].startAnimation(bobAnimation);
        }
    }

    public static void gameOver() {
        gameOver.setVisibility(View.VISIBLE);
    }

    public static int getBlockColor(int value) {

        switch (value) {
            case 1:
                return R.drawable.block1;
            case 2:
                return R.drawable.block2;
            case 3:
                return R.drawable.block3;
            case 4:
                return R.drawable.block4;
            case 5:
                return R.drawable.block5;
            case 6:
                return R.drawable.block6;
            case 7:
                return R.drawable.block7;
            case 8:
                return R.drawable.block8;
            case 9:
                return R.drawable.block9;
            case 10:
                return R.drawable.block10;
            case 11:
                return R.drawable.block11;
        }
        throw new IllegalArgumentException();
    }

}
