package cyrussa.github.com.words.Models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song implements Serializable {
    String title;
    String artist;

    @Override
    public boolean equals(Object o) {
        Song song;
        if (o instanceof Song) {
            song = (Song)o;
        } else {
            return false;
        }

        if (song == this) return true;

        return this.title.equals(song.title) && this.artist.equals(song.artist);
    }
}
