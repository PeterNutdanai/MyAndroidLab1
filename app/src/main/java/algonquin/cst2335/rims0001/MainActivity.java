package algonquin.cst2335.rims0001;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity","In onCreate() - Loading Widgets");

        Log.d(TAG,"Message");
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