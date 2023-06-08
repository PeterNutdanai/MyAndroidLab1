package algonquin.cst2335.rims0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent fromPrevious = getIntent();

        // null if EMAIL is not found
        String EMAIL = fromPrevious.getStringExtra("Email");
        String day = fromPrevious.getStringExtra("DAY"); // Sunday

        int age = fromPrevious.getIntExtra("AGE",0); // age = 26
        int something = fromPrevious.getIntExtra("SOMETHING",0);

        binding.textView3.setText("Welcome back " + EMAIL + " and " + day + " and " + age);
        binding.goBackButton.setOnClickListener ( (clk) -> {

            finish();
        });

        setContentView(R.layout.activity_second);

    }
}