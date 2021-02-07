package cyrussa.github.com.words.Models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Song implements Serializable {
    String title;
    String artist;
}
