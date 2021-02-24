package cyrussa.github.com.words;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.ViewModels.LyricsActivityViewModel;

public class DisplayLyricsActivity extends AppCompatActivity {

    private LyricsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lyrics);

        viewModel = new ViewModelProvider(this).get(LyricsActivityViewModel.class);
        viewModel.initialize();

        Song song = (Song) getIntent().getSerializableExtra("song");
        TextView display = findViewById(R.id.display);

        viewModel.lyrics().observe(this, display::setText);
        viewModel.fetchLyrics(song);
    }
}
