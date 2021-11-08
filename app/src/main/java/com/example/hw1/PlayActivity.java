package com.example.hw1;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {


    private ImageView rightButton,leftButton;
    private LinearLayout carBox;
    private ImageView[] hearts;
    private int carPositionNum;
    private TableLayout rocks;
    private Timer myTimer;
    private int index; // index
    private int accidentCount; // number of crashs
    private int SPEED = 1000; // speed of rocks move
    private Random rnd ;
    private final int HEARTS_NUM = 3;
    private final int COLS = 3; // number of cols
    private final int RATE = 3; // rate to add rocks in screen
    private final int ROWS = 7; // rows number
    private final int MAX_LINES_NUM = 3; // max lines number
    private final int MIN_LINES_NUM = 1; // min lines number
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        InitializeVariables();
        FindViews();
        onClickListeners();
        GenerateRocks();

    }

    private void GenerateRocks() {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, SPEED);
    }


    private void TimerMethod() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                //This method runs in the same thread as the UI.

                if(index % RATE == 0 ) {

                    showRock(0, getRandomRockPos());



                }

                updateRocks();
                checkCrash();
                index++;
            }
        });
    }

    private void updateRocks() {
        //update rocks location
        for(int i = index%RATE; i < rocks.getChildCount(); i+=RATE){
            TableRow row = (TableRow) rocks.getChildAt(i);
            for(int j = 0 ; j < row.getChildCount(); j++){
                ImageView img = (ImageView) row.getChildAt(j);
                //image visible then invisible and visible the image below it
                if(img.getVisibility() == View.VISIBLE){
                    img.setVisibility(View.INVISIBLE);
                    if(i + 1 < rocks.getChildCount())
                        showRock(i+1, j);
                }
            }
        }
    }

    private void checkCrash() {
        //check if car crashed
        TableRow row = (TableRow) rocks.getChildAt(ROWS-2);

        for(int i = 0; i < row.getChildCount(); i++){
            ImageView img = (ImageView) row.getChildAt(i);
            //car is crashed
            if(img.getVisibility() == View.VISIBLE && carPositionNum == i+1){
                accidentCount += 1;
                // if still have hears decrement by one
                if(accidentCount < HEARTS_NUM) {
                    playSound(R.raw.crash);// play crash sound
                    hearts[HEARTS_NUM - accidentCount].setVisibility(View.INVISIBLE);
                }
                else{
                    // no hears left then game over
                    hearts[0].setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_SHORT).show();
                    myTimer.cancel();
                    playSound(R.raw.game_over);//play game over sound
                }
            }
        }

    }

    public void playSound(int soundId){
        //play sound effect
        MediaPlayer mPlayer = MediaPlayer.create(PlayActivity.this, soundId);
        mPlayer.start();
    }


    private void showRock(int i, int j) {
        // visible rock image in i,j index
        TableRow row = (TableRow) rocks.getChildAt(i);
        ImageView img  = (ImageView) row.getChildAt(j);
        img.setVisibility(View.VISIBLE);
    }


    public int getRandomRockPos(){
        //get random number of cols
        return rnd.nextInt(COLS);
    }

    private void InitializeVariables() {
        carPositionNum = 2; // 1 - line 1, 2 - line 2, 3 - line 3
        index = 0;
        rnd = new Random();
        hearts = new ImageView[HEARTS_NUM];
        accidentCount = 0;

    }

    private void onClickListeners() {
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(carPositionNum < MAX_LINES_NUM) {
                    JumpRight();
                }
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(carPositionNum > MIN_LINES_NUM) {
                    JumpLeft();
                }
            }
        });

    }

    private void JumpLeft() {
        carPositionNum--;
        updateCarPosition();
    }

    private void updateCarPosition() {
        for(int i = 0; i < carBox.getChildCount(); i++){
            ImageView car = (ImageView) carBox.getChildAt(i);
            car.setVisibility(View.INVISIBLE);
        }

        ((ImageView) carBox.getChildAt(carPositionNum - 1)).setVisibility(View.VISIBLE);
    }

    private void JumpRight() {
        carPositionNum++;
        updateCarPosition();
    }

    private void FindViews() {
        rightButton = (ImageView)findViewById(R.id.Button_right);// Initialize right button
        leftButton = (ImageView)findViewById(R.id.Button_left);// Initialize left button
        carBox = (LinearLayout)findViewById(R.id.car);//Initialize car
        rocks = findViewById(R.id.Table_rocks);
        hearts[0] = findViewById(R.id.heart1);
        hearts[1] = findViewById(R.id.heart2);
        hearts[2] = findViewById(R.id.heart3);

    }
}