package cyrussa.github.com.words;

import android.content.Intent;
import android.database.Cursor;
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

import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.LyricsService;
import cyrussa.github.com.words.Services.VolleyHelper;


public class MainActivity extends AppCompatActivity {
    EditText songTitle, artist;
    Button getLyrics;
    TextView display;
    LyricsService lyricsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songTitle = findViewById(R.id.songTitle);
        artist = findViewById(R.id.artist);
        getLyrics = findViewById(R.id.getLyrics);
        display = findViewById(R.id.display);
        VolleyHelper.init(this);
        lyricsService = new LyricsService();
    }

    public void getLyrics(View view) {
        Song song = new Song(songTitle.getText().toString(), artist.getText().toString());
        lyricsService.getLyrics(song, this);
    }

    public void displayHistory(View view){
//        StringBuilder sb = new StringBuilder();
//        if(data.getCount() == 0){
//            sb.append("No Data Found");
//        } else {
//            for(data.moveToLast(); !data.isBeforeFirst(); data.moveToPrevious()){
//                sb.append(data.getString(0) + " by " + data.getString(1) + "\n");
//            }
//        }
//        Intent intent =new Intent(this, DisplayHistoryActivity.class);
//        intent.putExtra("history", sb.toString());
//        startActivity(intent);
    }

    public void clearHistory(View view){
        Intent intent = new Intent(this, SearchDisplayActivity.class);
        startActivity(intent);
    }
}