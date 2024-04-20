package com.example.plant_dis_det_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class unhealthy_aerca extends AppCompatActivity {
    TextView areca_ref_1,areca_ref_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unhealthy_aerca);
        areca_ref_1=findViewById(R.id.areca_ref_1);
        areca_ref_2=findViewById(R.id.areca_ref_2);
        areca_ref_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://krishi.icar.gov.in/jspui/bitstream/123456789/14523/1/ADAM.pdf"));
                startActivity(intent);
            }
        });
        areca_ref_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://cpcri.icar.gov.in/filemgr/webfs/publication/ext2.pdf"));
                startActivity(intent);
            }
        });

    }
}