package com.example.plant_dis_det_v1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SENSOR_VALUE extends AppCompatActivity {

    int moisture;
    int temperature=0;
    int humidity=0;

    circulargaugeview gauge1;
    circulargaugeview2 gauge2;
    circulargaugeview3 gauge3;
    DatabaseReference ref1,ref2,ref3;
    Button soil_button;
    TextView soil_condition;
    TextView M_T,T_T,H_T;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_value);

        gauge1 = findViewById(R.id.gauge1);
        gauge2 = findViewById(R.id.gauge2);
        gauge3 = findViewById(R.id.gauge3);
        soil_button = findViewById(R.id.soil_button);
        M_T=findViewById(R.id.moisture_text);
        T_T=findViewById(R.id.temperature_text);
        H_T=findViewById(R.id.humidity_text);

        ref1 = FirebaseDatabase.getInstance().getReference().child("Moisture");
        Log.d("Value +++++++++",ref1.toString());

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                moisture = dataSnapshot.getValue(Integer.class);
                gauge1.setProgress(moisture);
                gauge1.invalidate();
                Log.d(TAG, "Value is: " + moisture);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ref2 = FirebaseDatabase.getInstance().getReference().child("Temperature");
        Log.d("Value +++++++++",ref2.toString());

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temperature = dataSnapshot.getValue(Integer.class);
                gauge2.setProgress(temperature);
                gauge2.invalidate();
                Log.d(TAG, "Value is: " + temperature);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ref3 = FirebaseDatabase.getInstance().getReference().child("Humidity");
        Log.d("Value +++++++++",ref3.toString());

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                humidity = dataSnapshot.getValue(Integer.class);
                gauge3.setProgress(humidity);
                gauge3.invalidate();
                Log.d(TAG, "Value is: " + humidity);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        soil_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (humidity>40 && humidity<60) {
//                    Toast.makeText(SENSOR_VALUE.this, "Ideal Condition", Toast.LENGTH_LONG).show();
                    H_T.setText("Ideal Condition");
                } else {
//                    Toast.makeText(SENSOR_VALUE.this, "Not An Ideal Condition", Toast.LENGTH_LONG).show();
                    H_T.setText("Not An Ideal Condition");
                }
                if (moisture>2000){
//                    Toast.makeText(SENSOR_VALUE.this, "Soil Is Dry", Toast.LENGTH_LONG).show();
                    M_T.setText("Soil Is Dry");
                } else {
//                    Toast.makeText(SENSOR_VALUE.this, "Soil Is Dry", Toast.LENGTH_LONG).show();
                    M_T.setText("Soil Is Wet");
                }
                if (temperature>22 && temperature<30){
//                    Toast.makeText(SENSOR_VALUE.this, "Is Suitable For The Crop", Toast.LENGTH_LONG).show();
                    T_T.setText("Is Suitable For The Crop");
                } else {
//                    Toast.makeText(SENSOR_VALUE.this, "Not Suitable For The Crop", Toast.LENGTH_LONG).show();
                    T_T.setText("Not Suitable For The Crop");
                }
            }
        });

    }
}













/*package com.example.plant_dis_det_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//public class MainActivity extends AppCompatActivity {
public class SENSOR_VALUE extends AppCompatActivity {

    FirebaseFirestore fb;
    int moisture=0;
    int temperature=0;
    int humidity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_value);

        fb = FirebaseFirestore.getInstance();
        Log.d("+++++++++",fb.toString());

        circulargaugeview gauge1 = findViewById(R.id.gauge1);
        circulargaugeview gauge2 = findViewById(R.id.gauge2);
        circulargaugeview gauge3 = findViewById(R.id.gauge3);

        CollectionReference usersRef = fb.collection("Record");

        fb.collection("Record").document("MZwx8okWCmz7V4cgNpEQ")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("++++++++",(documentSnapshot.getData()).toString());
                        moisture = documentSnapshot.getLong("Moisture").intValue();
                        Log.d("++++++C", String.valueOf(moisture));
                        temperature = (int) documentSnapshot.getLong("Temperature").intValue();
                        Log.d("++++++C",String.valueOf(temperature));
                        humidity = (int) documentSnapshot.getLong("Humidity").intValue();
                        Log.d("++++++C",String.valueOf(humidity));
                        gauge1.setProgress(moisture);
                        gauge2.setProgress(temperature);
                        gauge3.setProgress(humidity);
                    }
                });
        Log.d("++++++C", String.valueOf(moisture));
        Log.d("++++++C",String.valueOf(temperature));
        Log.d("++++++C",String.valueOf(humidity));

    }
}
*/




