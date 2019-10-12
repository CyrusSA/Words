package cyrussa.github.com.words;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DisplayLyricsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lyrics);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String lyrics = intent.getStringExtra("lyrics");

        // Capture the layout's TextView and set the string as its text
        TextView display = findViewById(R.id.display);
        display.setText(lyrics);
    }
}
