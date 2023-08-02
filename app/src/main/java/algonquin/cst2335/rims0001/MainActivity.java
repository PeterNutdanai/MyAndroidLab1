package algonquin.cst2335.rims0001;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tv = null;
    private EditText et = null;
    private Button bt = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.pwd);
        bt = findViewById(R.id.loginBtn);

        bt.setOnClickListener( clk -> {
            String pwdString = et.getText().toString();
            checkPasswordComplexity(pwdString);
        });
    }

    public boolean checkPasswordComplexity(String s) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i = 0; i< s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                foundNumber = true;
            } else if(Character.isLowerCase(c)){
                foundLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }
        if(!foundUpperCase) {
            Toast.makeText(this, "Password require at least 1 upper case letter", Toast.LENGTH_LONG).show();
            tv.setText("You shall now pass or join the dark side and I will let you pass!");
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this,"Password require at least 1 lower case letter", Toast.LENGTH_LONG).show();
            tv.setText("You shall now pass or join the dark side and I will let you pass!");
        } else if (!foundNumber) {
            Toast.makeText(this, "Password require at least 1 number", Toast.LENGTH_LONG).show();
            tv.setText("You shall now pass or join the dark side and I will let you pass!");
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Password require at least 1 special letter", Toast.LENGTH_LONG).show();
            tv.setText("You shall now pass or join the dark side and I will let you pass!");
            return false;
        }
        tv.setText("Your password meets the requirements! ");
        return true;
    }
    boolean isSpecialCharacter(char c) {
        String specialCharacters = "#$%^&*!@?";
        String regex = "[" + Pattern.quote(specialCharacters) + "]";
        return String.valueOf(c).matches(regex);

    }
}