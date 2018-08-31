package cyrussa.github.com.words;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;



public class MainActivity extends AppCompatActivity {

    EditText songTitle, artist;
    Button getLyrics;
    TextView display;
    RequestQueue requestQueue;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songTitle = findViewById(R.id.songTitle);
        artist = findViewById(R.id.artist);
        getLyrics = findViewById(R.id.getLyrics);
        display = findViewById(R.id.display);

        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
    }

    private void sendLyrics(String lyricsString){
        Intent intent =new Intent(this, DisplayLyricsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, lyricsString);
        startActivity(intent);
    }

    public void getLyrics(View view) {
        String request = "https://api.lyrics.ovh/v1/" + artist.getText().toString() + "/" + songTitle.getText().toString() + "/";
        JsonObjectRequest objReq = new JsonObjectRequest(Request.Method.GET, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            sendLyrics(response.getString("lyrics"));
                        } catch (JSONException e) {
                            // If there is an error then output this to the logs.
                            Log.e("Volley", "Invalid JSON Object.");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }

        );

        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(objReq);
    }
}
