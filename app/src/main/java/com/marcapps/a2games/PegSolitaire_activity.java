package com.marcapps.a2games;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;
import com.marcapps.a2games.gameClasses.BoardPS;
import com.marcapps.a2games.gameClasses.PegBoards;

import java.util.InputMismatchException;

public class PegSolitaire_activity extends AppCompatActivity {


    //Attributes


    public static ImageView[][] views = new ImageView[7][7];
    public static final int[][] viewIds = new int[][]{
            {R.id.p00, R.id.p01, R.id.p02, R.id.p03, R.id.p04, R.id.p05, R.id.p06},
            {R.id.p10, R.id.p11, R.id.p12, R.id.p13, R.id.p14, R.id.p15, R.id.p16},
            {R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26},
            {R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p35, R.id.p36},
            {R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46},
            {R.id.p50, R.id.p51, R.id.p52, R.id.p53, R.id.p54, R.id.p55, R.id.p56},
            {R.id.p60, R.id.p61, R.id.p62, R.id.p63, R.id.p64, R.id.p65, R.id.p66}
    };
    public static BoardPS board;
    public static TextView txt;
    public static TextView debugText;
    public static ChipGroup boardSelector;
    public static Animation appear;
    public static Animation disappear;
    public static Animation upAnimation;
    public static Animation downAnimation;
    public static Animation rightAnimation;
    public static Animation leftAnimation;
    public static MediaPlayer pegSound;
    public static Chronometer chronometer;
    public static boolean chronoRunning = false;

    //OnCreate


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peg_solitaire);

        txt = findViewById(R.id.peg_txt);
        debugText = findViewById(R.id.debugText);
        boardSelector = findViewById(R.id.boardSelector);
        appear = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.appear_bounce);
        disappear = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.disappear_bounce);
        upAnimation = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.peg_up);
        downAnimation = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.peg_down);
        rightAnimation = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.peg_right);
        leftAnimation = AnimationUtils.loadAnimation(PegSolitaire_activity.this, R.anim.peg_left);
        pegSound = MediaPlayer.create(this, R.raw.peg_sound);
        chronometer = findViewById(R.id.chrono);

        views = new ImageView[7][7];

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                views[x][y] = findViewById(viewIds[x][y]);
            }
        }
        board = new BoardPS(PegBoards.STANDARD);
        updateWholeBoard(board);
        board.unselect();
        board.generateDebugText();
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


    // Methods


    public void activateDebugMode(View v) {
        debugText.setVisibility(View.VISIBLE);
    }

    public static void animatePeg(int x, int y, String animation) {
        switch (animation) {
            case "up":
                views[x][y].startAnimation(upAnimation);
                break;
            case "down":
                views[x][y].startAnimation(downAnimation);
                break;
            case "right":
                views[x][y].startAnimation(rightAnimation);
                break;
            case "left":
                views[x][y].startAnimation(leftAnimation);
                break;
        }
    }

    public void changeBoard(View v) {

        switch (v.getId()) {
            case R.id.Asymmetric:
                board = new BoardPS(PegBoards.ASYMMETRIC);
                updateWholeBoard(board);
                break;
            case R.id.five:
                board = new BoardPS(PegBoards.FIVE);
                updateWholeBoard(board);
                break;
            case R.id.iq:
                board = new BoardPS(PegBoards.IQ);
                updateWholeBoard(board);
                break;
            case R.id.french:
                board = new BoardPS(PegBoards.FRENCH);
                updateWholeBoard(board);
                break;
            case R.id.standard:
                board = new BoardPS(PegBoards.STANDARD);
                updateWholeBoard(board);
                break;
            case R.id.UFO:
                board = new BoardPS(PegBoards.UFO);
                updateWholeBoard(board);
                break;
        }
        txt.clearAnimation();
        txt.setVisibility(View.INVISIBLE);
        boardSelector.clearAnimation();
        boardSelector.setVisibility(View.INVISIBLE);
        chronoReset();
    }

    public void changeBoardButton(View v) {

        txt.clearAnimation();

        if (boardSelector.getVisibility() == View.VISIBLE) {
            boardSelector.clearAnimation();
            boardSelector.setVisibility(View.INVISIBLE);
            board.checkGameStatus();
        } else {
            txt.setVisibility(View.INVISIBLE);
            boardSelector.setVisibility(View.VISIBLE);
            boardSelector.startAnimation(appear);
        }
    }

    public static void chronoStart() {
        if (!chronoRunning) {
            chronoReset();
            chronometer.start();
            chronoRunning = true;
        }
    }

    public static char getChronoText() {
        CharSequence s = chronometer.getContentDescription();
        System.out.println(s);
        return s.charAt(0);
    }

    public static void chronoStop() {
        if (chronoRunning) {
            chronometer.stop();
            chronoRunning = false;
        }
    }

    public static void chronoReset() {
        chronoStop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public static int[] locateViewById(int id) {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (viewIds[x][y] == id) {
                    return new int[]{x, y};
                }
            }
        }
        throw new InputMismatchException();
    }

    public static void pegSound() {
        pegSound.start();
    }

    public void restart(View v) {
        String name = board.getName();
        this.board = new BoardPS(PegBoards.getBoard(name));
        updateWholeBoard(board);
        txt.clearAnimation();
        txt.setVisibility(View.INVISIBLE);
        chronoReset();
    }

    public void select(View view) {
        int[] pos = locateViewById(view.getId());
        board.selectPeg(pos[0], pos[1]);
        boardSelector.clearAnimation();
        boardSelector.setVisibility(View.INVISIBLE);
    }

    public void undo(View v) {
        board.undo();
        txt.clearAnimation();
        txt.setVisibility(View.INVISIBLE);
    }

    public static void updateDebugText(String txt) {
        debugText.setText(txt);
    }

    public static void updateGameStatus(String s) {
        txt.setVisibility(View.VISIBLE);
        txt.setText(s);
        txt.startAnimation(appear);
    }

    public static void updatePegImage(int x, int y, int value) {

        ImageView v = (ImageView) views[x][y];

        switch (value) {
            case BoardPS.NOTHING:
                v.setVisibility(View.INVISIBLE);
                break;
            case BoardPS.PEG:
                v.setVisibility(View.VISIBLE);
                v.setImageResource(R.drawable.peg);
                break;
            case BoardPS.PEG_SELECTED:
                v.setVisibility(View.VISIBLE);
                v.setImageResource(R.drawable.peg_selected);
                break;
            case BoardPS.HOLE:
                v.setVisibility(View.VISIBLE);
                v.setImageResource(R.drawable.no_peg);
                break;
            default:
                throw new InputMismatchException();
        }

    }

    public static void updateWholeBoard(BoardPS board) {

        int[][] boxes = board.getBoxes();

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                updatePegImage(x, y, boxes[x][y]);
            }
        }

    }
}