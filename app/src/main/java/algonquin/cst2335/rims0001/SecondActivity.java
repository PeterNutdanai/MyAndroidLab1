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
        setContentView(R.layout.activity_second);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent fromPrevious = getIntent();

        // null if EMAIL is not found
        String EMAIL = fromPrevious.getStringExtra("Email");
        String day = fromPrevious.getStringExtra("DAY"); // Sunday

        int age = fromPrevious.getIntExtra("AGE", 0); // age = 26
        int something = fromPrevious.getIntExtra("SOMETHING", 0);

        binding.textView.setText("Welcome back " + EMAIL + " and " + day + " and " + age);
        binding.goBackButton.setOnClickListener((clk) -> {
            finish();
        });

        profileImage = findViewById(R.id.imageView); // Initialize profileImage ImageView

        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel: " + "3435585543"));
        startActivity(call);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                                startActivity(cameraIntent);
                            else
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, 20);

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profileImage.setImageBitmap(thumbnail);
                        }
                    }
                });
        cameraResult.launch(cameraIntent);
    }
}
