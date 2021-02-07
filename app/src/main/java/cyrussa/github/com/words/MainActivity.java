package cyrussa.github.com.words;

import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.Api.Spotify;
import cyrussa.github.com.words.Services.LyricsService;
import cyrussa.github.com.words.Services.SongSearchService;
import cyrussa.github.com.words.Services.VolleyHelper;


public class MainActivity extends AppCompatActivity {
    EditText songTitle, artist;
    Button getLyrics;
    TextView display;
    LyricsService lyricsService;
    SongSearchService songSearchService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To enable network calls in UI thread, should be removed
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        songTitle = findViewById(R.id.songTitle);
        artist = findViewById(R.id.artist);
        getLyrics = findViewById(R.id.search_button);
        display = findViewById(R.id.display);
        VolleyHelper.init(this);
        lyricsService = new LyricsService();
        songSearchService = new SongSearchService(this);
    }

    public void search(View view) {
        songSearchService.search(this, songTitle.getText().toString());
    }

    public void getLyrics(View view) {
        Song song = new Song(songTitle.getText().toString(), artist.getText().toString());
        lyricsService.getLyrics(song, this);
    }

    public void displayHistory(View view) throws IOException {
        // Here for testing purposes
        Spotify spotify = new Spotify(this);
        spotify.authenticate();
    }

    public void clearHistory(View view){
        Intent intent = new Intent(this, SearchDisplayActivity.class);
        startActivity(intent);
    }
}