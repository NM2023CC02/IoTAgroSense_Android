package com.example.plant_dis_det_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class unhealthy_pepper extends AppCompatActivity {
    TextView pepper_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unhealthy_pepper);
        pepper_ref=findViewById(R.id.pepper_ref);
        pepper_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://kirehalli.com/pollu-disease-in-black-pepper/"));
                startActivity(intent);
            }
        });
    }
}