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
import android.widget.ImageView;

import algonquin.cst2335.rims0001.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent fromPrevious = getIntent();

        // Get values from the intent extras
        String email = fromPrevious.getStringExtra("Email");
        String day = fromPrevious.getStringExtra("DAY");
        int age = fromPrevious.getIntExtra("AGE", 0);

        // Set the text in the TextView
        binding.textView.setText("Welcome back " + email + " and " + day + " and " + age);

        // Set a click listener for the "Go Back" button
        binding.goBackButton.setOnClickListener(clk -> {
            finish();
        });

        // Find the ImageView by its ID
        profileImage = findViewById(R.id.imageView);

        // Start dialing a phone number
        binding.callNumber.setOnClickListener( clk -> {
            String phoneNumber = "3435585543";
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        // Launch the camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                // Only start the camera if permission is granted
                                startActivity(cameraIntent);
                            } else {
                                // Request camera permission
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, 20);
                            }

                            // Get the captured image from the result
                            Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                    // Set the captured image to the ImageView
                                    profileImage.setImageBitmap(thumbnail);
                                }
                            }
                });
        // Change Picture
        binding.changePicture.setOnClickListener( clk ->{
            // Launch the camera activity
            cameraResult.launch(cameraIntent);
        });

    }
}
