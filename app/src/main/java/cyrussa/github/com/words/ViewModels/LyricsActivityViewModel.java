package cyrussa.github.com.words.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.Repositories.LyricsRepository;
import lombok.Getter;
import lombok.Setter;

public class LyricsActivityViewModel extends ViewModel {
    @Getter
    @Setter
    private Song song;

    private final MutableLiveData<String> lyrics = new MutableLiveData<>();
    private LyricsRepository lyricsRepository;

    public void initialize() {
        this.lyricsRepository = new LyricsRepository();
    }

    public LiveData<String> lyrics() {
        return this.lyrics;
    }

    public void fetchLyrics(Song song) {
        this.song = song;
        this.lyricsRepository.getLyrics(song.getArtists().get(0), song.getTitle(), lyrics::setValue);
    }
}
