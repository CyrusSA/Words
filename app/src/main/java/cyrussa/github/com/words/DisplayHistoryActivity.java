package cyrussa.github.com.words;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class DisplayHistoryActivity extends Activity {
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);
        display = findViewById(R.id.display);
        Intent intent = getIntent();
        String history = intent.getStringExtra("history");
        display.setText(history);
    }

}
