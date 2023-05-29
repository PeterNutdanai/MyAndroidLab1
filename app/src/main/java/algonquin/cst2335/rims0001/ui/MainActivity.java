package algonquin.cst2335.rims0001.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//import algonquin.cst2335.rims0001.ActivityMainBinding;
import algonquin.cst2335.rims0001.data.MainViewModel;
import algonquin.cst2335.rims0001.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent onCreate()

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //loads XML on screen

        //listen for change to MutableLiveData
        model.isSelected.observe(this, selected -> {
            //inOn has changed and the new value is newValue
            variableBinding.theCheckbox.setChecked(selected);
            variableBinding.theSwitch.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);

            String message = "The value is now: " + model.isSelected.getValue();
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        });

        //lambda notation:
        variableBinding.theCheckbox.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.theSwitch.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.radioButton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        Button btn = variableBinding.mybutton;
        btn.setOnClickListener( click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });
        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has " + s);
        });
        variableBinding.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        variableBinding.myimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = v.getWidth();
                int height = v.getHeight();

                String message="The width = " + width + "and height = " + height;
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onPictureClick(View v){

    }

}