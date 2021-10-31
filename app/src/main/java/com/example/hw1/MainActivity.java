package com.example.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    ImageView background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FindViews();

    }

//    private void FindViews() {
//        background = findViewById(R.id.welcome_page_backGround);
//        Glide.with(this)
//                .load("https://static.vecteezy.com/system/resources/previews/001/849/553/original/modern-gold-background-free-vector.jpg")
//                .into(background);
//    }


}