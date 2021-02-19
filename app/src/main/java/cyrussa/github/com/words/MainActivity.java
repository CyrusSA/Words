package cyrussa.github.com.words;

import android.content.Intent;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.Api.OvhLyricsService;
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
        getLyrics = findViewById(R.id.search_button);
        display = findViewById(R.id.display);
        VolleyHelper.init(this);
        lyricsService = new OvhLyricsService();
    }

    public void search(View view) {
        Intent searchActivityIntent = new Intent(this, SearchActivity.class);
        searchActivityIntent.putExtra("query", songTitle.getText().toString());

        this.startActivity(searchActivityIntent);
    }

    public void getLyrics(View view) {
        Song song = new Song(songTitle.getText().toString(), artist.getText().toString());
        lyricsService.getLyrics(this, song);
    }

    public void displayHistory(View view) {
    }

    public void clearHistory(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}