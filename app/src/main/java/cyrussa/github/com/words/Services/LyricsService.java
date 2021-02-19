package cyrussa.github.com.words.Services;

import androidx.appcompat.app.AppCompatActivity;
import cyrussa.github.com.words.Models.Song;

public interface LyricsService {
    void getLyrics(AppCompatActivity mainActivity, Song song);
}
