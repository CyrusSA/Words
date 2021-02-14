package cyrussa.github.com.words.Services.Api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import cyrussa.github.com.words.DisplayLyricsActivity;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.LyricsService;
import cyrussa.github.com.words.Services.VolleyHelper;

public class OvhLyricsService implements LyricsService {
    private VolleyHelper volleyHelper;

    public OvhLyricsService(){
        volleyHelper = VolleyHelper.getInstance();
    }

    public void getLyrics(AppCompatActivity mainActivity, Song song){
        String url = "https://api.lyrics.ovh/v1/" + song.getArtist() + "/" + song.getTitle() + "/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
