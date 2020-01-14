package cyrussa.github.com.words.Services;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import cyrussa.github.com.words.DisplayLyricsActivity;
import cyrussa.github.com.words.MainActivity;
import cyrussa.github.com.words.Models.Lyrics;
import cyrussa.github.com.words.Models.Song;

public class LyricsService {
    private VolleyHelper volleyHelper;

    public LyricsService(){
        volleyHelper = VolleyHelper.getInstance();
    }

    public void getLyrics(Song song, MainActivity mainActivity){
        String url = "https://api.lyrics.ovh/v1/" + song.getArtist() + "/" + song.getTitle() + "/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                response -> {
                    Intent intent =new Intent(mainActivity, DisplayLyricsActivity.class);
                    try {
                        intent.putExtra("lyrics", response.getString("lyrics"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mainActivity.startActivity(intent);
                },
                error -> {
                    Log.e("Volley", error.toString());
                }
        );
        volleyHelper.queueRequest(request);
    }
}
