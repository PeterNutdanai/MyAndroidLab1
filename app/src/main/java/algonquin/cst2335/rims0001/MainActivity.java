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


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    protected ActivityMainBinding binding;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w(TAG, "In onCreate() - Loading Widgets");

        Log.d(TAG, "Message");
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);

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


        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel: " + "3435585543"));

        startActivity(call);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> camaraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                                startActivity(cameraIntent);
                            else
                                requestPermissions(new String[] {Manifest.permission.CAMERA}, 20);

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap( thumbnail );
                        }
                    }
                });
                camaraResult.launch(cameraIntent);

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if((requestCode == 20) && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            startActivity( new Intent(MediaStore.ACTION_IMAGE_CAPTURE) );
//    }

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