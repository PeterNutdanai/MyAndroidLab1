package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.rims0001.databinding.ActivityMainBinding;

/**
 *
 * @author Nutdanai Rimsakul
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */


    protected RequestQueue queue = null;
    protected String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );

        binding.getForecast.setOnClickListener(click -> {
            cityName = binding.editText.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="+ URLEncoder.encode(cityName,"UTF-8") +"&appid=7e943c97096a9784391a981c4d878b22&Units=Metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null, (response) -> {
                int j = 0;
            }, (error) -> {
                int i = 0;
            });
            queue.add(request);
        });
    }



//    /**
//     * This is a function for checkPasswordComplexity
//     *
//     * @param pw The String object that we are checking
//     * @return Return true if the password is right format
//     */
//    boolean checkPasswordComplexity(String pw) {
//        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
//        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
//
//        for (char c : pw.toCharArray()) {
//            if (Character.isUpperCase(c)) {
//                foundUpperCase = true;
//            } else if (Character.isLowerCase(c)) {
//                foundLowerCase = true;
//            } else if (Character.isDigit(c)) {
//                foundNumber = true;
//            } else {
//                foundSpecial = true;
//            }
//        }
//
//        // Check if all requirements are met
//       // return foundUpperCase && foundLowerCase && foundNumber && foundSpecial;
//
//        if (!foundUpperCase) {
//
//            Toast.makeText(MainActivity.this, "Password is missing an uppercase letter.", Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;
//
//            return false;
//
//        } else if (!foundLowerCase) {
//            Toast.makeText(MainActivity.this, "Password is missing a lowercase letter.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
//
//            return false;
//
//        } else if (!foundNumber) {
//            Toast.makeText(MainActivity.this, "Password is missing a number.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
//            return false;
//
//        } else if (!foundSpecial) {
//            Toast.makeText(MainActivity.this, "Password is missing a special case.", Toast.LENGTH_SHORT).show(); // Say that they are missing a lower case letter;
//            return false;
//
//        } else {
//
//            return true; //only get here if they're all true
//
//        }
//    }
//    /**
//     *Checks if a given character is a special character.
//     * @param c The character to check.
//     * @return true if the character is a special character, false otherwise.
//     */
//    boolean isSpecialCharacter ( char c)
//
//    {
//        switch (c) {
//            case '#':
//            case '?':
//            case '*':
//            case '$':
//            case '%':
//            case '^':
//            case '@':
//            case '!':
//            case '&':
//                return true;
//            default:
//                return false;
//        }
//    }
}
