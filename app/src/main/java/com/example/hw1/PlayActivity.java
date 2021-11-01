package com.example.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {


    private ImageView rightButton,leftButton, car;
    private ImageView[] hearts;
    private RelativeLayout.LayoutParams carPosition;
    private int carPositionNum;
    private TableLayout rocks;
    private Timer myTimer;
    private int count;
    private int accidentCount;
    private Random rnd ;
    private final int HEARTS_NUM = 3;
    private final int COLS = 3;
    private final int RATE = 3;
    private final int ROWS = 6;
    private final int SPEED = 1000;
    boolean toCreate; //true = creating new rock
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
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private void updateRocks() {

        for(int i = count%RATE; i < rocks.getChildCount(); i+=RATE){
            TableRow row = (TableRow) rocks.getChildAt(i);
            for(int j = 0 ; j < row.getChildCount(); j++){
                ImageView img = (ImageView) row.getChildAt(j);
                if(img.getVisibility() == View.VISIBLE){
                    img.setVisibility(View.INVISIBLE);
                    if(i + 1 < rocks.getChildCount())
                        showRock(i+1, j);
                }
            }
        }
    }

    private void checkAccident() {
        TableRow row = (TableRow) rocks.getChildAt(ROWS-1);

        for(int i = 0; i < row.getChildCount(); i++){
            ImageView img = (ImageView) row.getChildAt(i);
            if(img.getVisibility() == View.VISIBLE && carPositionNum == i+1){
                accidentCount += 1;
                if(accidentCount < HEARTS_NUM)
                    hearts[HEARTS_NUM - accidentCount].setVisibility(View.INVISIBLE);
                else{
                    Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }


    }


    private void showRock(int i, int j) {
        TableRow row = (TableRow) rocks.getChildAt(i);
        ImageView img  = (ImageView) row.getChildAt(j);
        img.setVisibility(View.VISIBLE);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here
            updateRocks();
            if(count % RATE == 0 ) {
                showRock(0, getRandomRockPos());
            }
            checkAccident();
            count++;
        }
    };

    public int getRandomRockPos(){
        return rnd.nextInt(COLS);
    }

    private void InitializeVariables() {
        carPositionNum = 2; // 1 - leftSide, 2 - center, 3 - rightSide
        toCreate = true;
        count = 0;
        rnd = new Random();
        hearts = new ImageView[HEARTS_NUM];
        accidentCount = 0;

    }

    private void onClickListeners() {
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(carPositionNum != 3) {
                    JumpRight();
                }
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(carPositionNum != 1) {
                    JumpLeft();
                }
            }
        });

    }

    private void JumpLeft() {
        if(carPositionNum == 2){
            carPosition = new RelativeLayout.LayoutParams(210,250);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            carPosition.leftMargin = 100;
            car.setLayoutParams(carPosition);
            carPositionNum = 1;

        }else if(carPositionNum == 3){
            carPosition = new RelativeLayout.LayoutParams(210,250);
            carPosition.addRule(RelativeLayout.CENTER_HORIZONTAL);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            car.setLayoutParams(carPosition);
            carPositionNum = 2;
        }
    }

    private void JumpRight() {
        if(carPositionNum == 2){
            carPosition = new RelativeLayout.LayoutParams(210,250);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            carPosition.rightMargin = 100;
            car.setLayoutParams(carPosition);
            carPositionNum = 3;

        }else if(carPositionNum == 1){
            carPosition = new RelativeLayout.LayoutParams(210,250);
            carPosition.addRule(RelativeLayout.CENTER_HORIZONTAL);
            carPosition.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            car.setLayoutParams(carPosition);
            carPositionNum = 2;
        }
    }

    private void FindViews() {
        rightButton = (ImageView)findViewById(R.id.Button_right);// Initialize right button
        leftButton = (ImageView)findViewById(R.id.Button_left);// Initialize left button
        car = (ImageView)findViewById(R.id.car);//Initialize car
        rocks = findViewById(R.id.Table_rocks);
        hearts[0] = findViewById(R.id.heart1);
        hearts[1] = findViewById(R.id.heart2);
        hearts[2] = findViewById(R.id.heart3);

    }
}