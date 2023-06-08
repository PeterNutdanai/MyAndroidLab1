package algonquin.cst2335.rims0001;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import algonquin.cst2335.rims0001.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    protected ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w(TAG, "In onCreate() - Loading Widgets");

        Log.d(TAG, "Message");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener( clk -> {
            Log.d(TAG, "You clicked the button");
        });

            // Code to be executed when the login button is clicked
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            String whatIsTyped = binding.emailText.getText().toString();

            nextPage.putExtra("Email",whatIsTyped);
            nextPage.putExtra("AGE",26);
            nextPage.putExtra("DAY","Sunday");

            startActivity(nextPage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG,"In onCreate() - Loading Widgets");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG,"In onCreate() - Loading Widgets");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG,"In onCreate() - Loading Widgets");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG,"In onCreate() - Loading Widgets");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG,"In onCreate() - Loading Widgets");

    }
}