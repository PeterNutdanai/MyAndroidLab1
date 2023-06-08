package algonquin.cst2335.rims0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    protected ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets");

        Log.d(TAG, "Message");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(clk -> {
            // Code to be executed when the login button is clicked
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(nextPage);
            Log.d(TAG, "You clicked the button");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity","In onCreate() - Loading Widgets");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity","In onCreate() - Loading Widgets");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity","In onCreate() - Loading Widgets");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity","In onCreate() - Loading Widgets");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity","In onCreate() - Loading Widgets");

    }
}