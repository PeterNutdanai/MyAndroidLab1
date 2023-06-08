package algonquin.cst2335.rims0001;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import algonquin.cst2335.rims0001.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    protected ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w(TAG, "In onCreate() - Loading Widgets");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Where can you save files:
        File myDir = getFilesDir();
        String path = myDir.getAbsolutePath();

        SharedPreferences prefs = getSharedPreferences("MyFile", Context.MODE_PRIVATE);

        // get an Editor
        SharedPreferences.Editor edit = prefs.edit();

        edit.putInt("Age",27);
        edit.putString("NAME","Peter");

        // save to disk
        edit.commit();


        binding.loginButton.setOnClickListener(clk -> {
            Log.d(TAG, "You clicked the button");

            // Code to be executed when the login button is clicked
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            String whatIsTyped = binding.emailText.getText().toString();

            nextPage.putExtra("Email", whatIsTyped);
            nextPage.putExtra("AGE", 26);
            nextPage.putExtra("DAY", "Sunday");

            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - Loading Widgets");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - Loading Widgets");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Loading Widgets");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - Loading Widgets");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestroy() - Loading Widgets");
    }
}