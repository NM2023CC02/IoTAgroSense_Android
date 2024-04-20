package com.example.plant_dis_det_v1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.plant_dis_det_v1.ml.PlantDiesaseTm;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.plant_dis_det_v1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {

    TextView result, demoTxt, classified, clickHere;
    ImageView imageView, arrowImage;
    ImageButton picture;
    int maxPos=0;

    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.image_View);
        picture = findViewById(R.id.button);

        demoTxt = findViewById(R.id.demoText);
        clickHere = findViewById(R.id.click_here);
        arrowImage = findViewById(R.id.demoArrow);
        classified = findViewById(R.id.classified);

        demoTxt.setVisibility(View.VISIBLE);
        clickHere.setVisibility(View.GONE);
//        arrowImage.setVisibility(View.VISIBLE);
        classified.setVisibility(View.GONE);
        result.setVisibility(View.GONE);

        picture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api= Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode==1 && resultCode==RESULT_OK){
            Bitmap image=(Bitmap) data.getExtras().get("data");
            int dimension=Math.min(image.getWidth(),image.getHeight());
            image= ThumbnailUtils.extractThumbnail(image,dimension,dimension);
            imageView.setImageBitmap(image);

            demoTxt.setVisibility(View.GONE);
            clickHere.setVisibility(View.VISIBLE);
//            arrowImage.setVisibility(View.GONE);
            classified.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);

            image=Bitmap.createScaledBitmap(image,imageSize,imageSize,false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    private void classifyImage(Bitmap image){
        try {
            PlantDiesaseTm model = PlantDiesaseTm.newInstance(getApplicationContext());
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*imageSize*imageSize*3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValue=new int[imageSize*imageSize];
            image.getPixels(intValue,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());

            int pixel=0;
            for(int i=0;i<imageSize;i++){
                for(int j=0;j<imageSize;j++){
                    int val=intValue[pixel++];
                    byteBuffer.putFloat(((val>>16)& 0xFF)*(1.f/255.f));
                    byteBuffer.putFloat(((val>>8)& 0xFF)*(1.f/255.f));
                    byteBuffer.putFloat((val & 0xFF)*(1.f/255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            PlantDiesaseTm.Outputs outputs=model.process(inputFeature0);
            TensorBuffer outputFeature0=outputs.getOutputFeature0AsTensorBuffer();
            float[] confidence = outputFeature0.getFloatArray();


            float maxConfidence=0;
            for(int i=0;i<confidence.length;i++){
                if(confidence[i]>maxConfidence){
                    maxConfidence=confidence[i];
                    maxPos=i;
                }
            }
//            String[] classes={"Healthy Pepper","Polludisease Anthacronose","Healthy Arecanut","Yellow leaf","Invalid Input"};
              String[] classes={"ಆರೋಗ್ಯಕರ ಕರಿಮೆಣಸು ಸಸ್ಯ","ಪೊಲ್ಯು ಆಂಥಾಕ್ರೋನೋಸ್","ಆರೋಗ್ಯಕರ ಅಡಿಕೆ ಮರ","ಹಳದಿ ಎಲೆ ರೋಗ","ಅಮಾನ್ಯ ಚಿತ್ರ"};
            result.setText(classes[maxPos]);
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = null;
                    if ("ಆರೋಗ್ಯಕರ ಕರಿಮೆಣಸು ಸಸ್".equals(result.getText().toString())) {
                        intent = new Intent(MainActivity.this, Healthy_plant.class);
                    } else if ("ಪೊಲ್ಯು ಆಂಥಾಕ್ರೋನೋಸ್".equals(result.getText().toString())) {
                        intent = new Intent(MainActivity.this, unhealthy_pepper.class);
                    } else if ("ಆರೋಗ್ಯಕರ ಅಡಿಕೆ ಮರ".equals(result.getText().toString())) {
                        intent = new Intent(MainActivity.this, Healthy_plant.class);
                    } else if ("ಹಳದಿ ಎಲೆ ರೋಗ".equals(result.getText().toString())) {
                        intent = new Intent(MainActivity.this, unhealthy_aerca.class);
                    } else if ("ಅಮಾನ್ಯ ಚಿತ್ರ".equals(result.getText().toString())) {
                        intent = new Intent(MainActivity.this, Invalid_input.class);
                    } else{
                        Toast.makeText(MainActivity.this, "NO OPTIONS", Toast.LENGTH_LONG).show();
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("https://www.google.com/search?q="+result.getText()+" PLANT")));
                }
            });
            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

