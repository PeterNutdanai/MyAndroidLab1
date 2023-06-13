package algonquin.cst2335.rims0001;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            boolean isComplex = checkPasswordComplexity(password);
            if (isComplex) {

            } else {
                Toast.makeText(MainActivity.this, "Password does not meet complexity requirements.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * This is a function for checkPasswordComplexity
     *
     * @param pw The String object that we are checking
     * @return Return true if the password is right format
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (char c : pw.toCharArray()) {
            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else {
                foundSpecial = true;
            }
        }

        // Check if all requirements are met
       // return foundUpperCase && foundLowerCase && foundNumber && foundSpecial;

        if (!foundUpperCase) {

            Toast.makeText(MainActivity.this, "Password is missing an uppercase letter.", Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;

            return false;

        } else if (!foundLowerCase) {
            Toast.makeText(MainActivity.this, "Password is missing a lowercase letter.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;

            return false;

        } else if (!foundNumber) {
            Toast.makeText(MainActivity.this, "Password is missing a number.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;

        } else if (!foundSpecial) {
            Toast.makeText(MainActivity.this, "Password is missing a special case.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
            return false;

        } else {

            return true; //only get here if they're all true

        }
    }
    /**
     *
     * @param c
     * @return
     */
    boolean isSpecialCharacter ( char c)

    {
        switch (c) {
            case '#':
            case '?':
            case '*':
            case '$':
            case '%':
            case '^':
            case '@':
            case '!':
            case '&':
                return true;
            default:
                return false;
        }
    }
}
