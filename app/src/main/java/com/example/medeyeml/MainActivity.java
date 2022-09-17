package com.example.medeyeml;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.nfc.Tag;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.medeyeml.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.gms.vision.detector.Detection;
import org.tensorflow.lite.task.gms.vision.detector.ObjectDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //TensorFlow Object Detection
    private void runObjectDetection(Bitmap bitmap) throws IOException {
        //Convert reg pics to tensorimages
        TensorImage image = TensorImage.fromBitmap(bitmap);

        //Initialize a detector object
        ObjectDetector.ObjectDetectorOptions options = ObjectDetector.ObjectDetectorOptions.builder().setMaxResults(5)
                .setScoreThreshold(0.5f)
                .build();
        ObjectDetector objectDetector = ObjectDetector.createFromFileAndOptions(this,"skinDetectorModel.tflite",options);

        //Perform detection
        List<Detection> results = objectDetector.detect(image);

        //debugPrint(results);

        //Draw & Get detection results
        List<String> diseases = new ArrayList<>();
        Canvas drawnResultsImg = new Canvas(bitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        for(Detection detection : results) {
            String curDiseaseFound = detection.getCategories().get(0).getLabel();
            diseases.add(curDiseaseFound);
            drawnResultsImg.drawRect(detection.getBoundingBox(), paint);
        }

        //TODO Figure out how to do change inputImageView
        //https://www.youtube.com/watch?v=vLxn5mOuWAk (31:37)
        MainActivity.this.runOnUiThread(new Runnable(){
            @Override
            public void run() {

            }
        });


    }

    //Print Results
    private void debugPrint(List<Detection> results){
        int i = 0;
        for(Detection detection : results){
            RectF box = detection.getBoundingBox();

            Log.d("Index of Object", Integer.toString(i));
            Log.d("Class of Object", detection.getCategories().get(0).getLabel());
            Log.d("Bounding Box", Float.toString(box.centerX()));

            i++;
        }
    }

}