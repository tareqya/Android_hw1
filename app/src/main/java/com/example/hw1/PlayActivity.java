package com.example.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {

    private RelativeLayout paths;
    private ImageView rightButton,leftButton, car;
    private RelativeLayout.LayoutParams carPosition;
    private int carPositionNum;

    private Timer myTimer;
    private ImageView rocks[];

    boolean toCreate; //true = creating new rock
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        InitializeVariables();
        FindViews();
        onClickListeners();
        //GenerateRocks();

    }

    private void GenerateRocks() {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);
    }


    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here

        }
    };


    private void InitializeVariables() {
        carPositionNum = 2; // 1 - leftSide, 2 - center, 3 - rightSide
        toCreate = true;
        rocks = new ImageView[4];
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
        paths = findViewById(R.id.paths);
        rightButton = (ImageView)findViewById(R.id.Button_right);// Initialize right button
        leftButton = (ImageView)findViewById(R.id.Button_left);// Initialize left button
        car = (ImageView)findViewById(R.id.car);//Initialize car
        rocks[0].setImageResource(R.drawable.ic_rock);
        rocks[1].setImageResource(R.drawable.ic_rock);
        rocks[2].setImageResource(R.drawable.ic_rock);
        rocks[3].setImageResource(R.drawable.ic_rock);

    }
}