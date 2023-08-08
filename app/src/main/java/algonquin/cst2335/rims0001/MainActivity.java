package algonquin.cst2335.rims0001;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate( getLayoutInflater() );

        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);

        binding.getForecast.setOnClickListener(click -> {
            cityName = binding.editText.getText().toString();
            String url = "https://api.openweathermap.org/data/2.5/weather?q="+ URLEncoder.encode(cityName) +"&appid=7e943c97096a9784391a981c4d878b22&Units=Metric";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {
                    JSONObject main = null;
                    try {
                        main = response.getJSONObject("main");
                        double temp = main.getDouble("temp");

                        binding.textView.setText("The temperature is " + temp + " degrees.");

                        JSONObject coord = response.getJSONObject("coord");
                        JSONArray weatherArray = response.getJSONArray ( "weather" );
                        JSONObject position0 = weatherArray.getJSONObject(0);

                        String description = position0.getString("description");
                        String iconName = position0.getString("icon");
                        JSONObject mainObject = response.getJSONObject("main");
                        double current = mainObject.getDouble("temp");
                        double min = mainObject.getDouble("temp_min");
                        double max = mainObject.getDouble("temp_max");
                        int humidity = mainObject.getInt("humidity");

                        runOnUiThread( (  )  -> {

                            binding.temp.setText("The current temperature is " + current);
                            binding.temp.setVisibility(View.VISIBLE);

                            binding.min.setText("The min temperature is " + min);
                            binding.min.setVisibility(View.VISIBLE);

                            binding.max.setText("The max temperature is " + max);
                            binding.max.setVisibility(View.VISIBLE);

                            binding.humidity.setText("The humidity is " + humidity);
                            binding.humidity.setVisibility(View.VISIBLE);

                            binding.description.setText("Description: " + description);
                            binding.description.setVisibility(View.VISIBLE);

                        });
                        String pathname = getFilesDir() + "/" + iconName + ".png";
                        File file = new File(pathname);

                        if (file.exists()) {
                            Bitmap image = BitmapFactory.decodeFile(pathname);
                            binding.weatherIcon.setImageBitmap(image);
                            binding.weatherIcon.setVisibility(View.VISIBLE);

                        } else {
                            String pictureURL = "https://openweathermap.org/img/w/" + iconName + ".png";

                            ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {

                                    try (FileOutputStream fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE)) {
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                        binding.weatherIcon.setImageBitmap(bitmap);
                                        binding.weatherIcon.setVisibility(View.VISIBLE);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {

                            });

                            queue.add(imgReq);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                int j = 0;
            }, (error) -> {
                int i = 0;
            });
            queue.add(request);

        });
    }
}
