package cyrussa.github.com.words.Services.Api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import java.util.function.Consumer;

import cyrussa.github.com.words.Services.VolleyHelper;

public class LyricsRepository {
    private final String LYRICS_ENDPOINT =  "https://api.lyrics.ovh/v1/%s/%s/";
    private VolleyHelper volleyHelper;

    public LyricsRepository(){
        volleyHelper = VolleyHelper.getInstance();
    }

    public void getLyrics(String songTitle, String artist, Consumer<String> callback){
        String url = String.format(LYRICS_ENDPOINT, artist, songTitle);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        callback.accept(response.getString("lyrics"));
                    } catch (JSONException e) {
                        callback.accept("");
                    }
                },
                error -> {
                    Log.e("Volley", error.toString());
                }
        );
        volleyHelper.queueRequest(request);
    }
}
