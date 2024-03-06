package com.example.plant_dis_det_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Select_Button extends AppCompatActivity{
    android.widget.Button button1, button2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_button);

        button1 = findViewById(R.id.s_button);
        button2 = findViewById(R.id.t_button);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    private void openActivity1() {
        Intent intent = new Intent(this, SENSOR_VALUE.class);
        startActivity(intent);
    }

    private void openActivity2() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
