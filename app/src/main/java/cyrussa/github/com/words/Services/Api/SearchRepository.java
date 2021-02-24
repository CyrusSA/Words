package cyrussa.github.com.words.Services.Api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import cyrussa.github.com.words.BuildConfig;
import cyrussa.github.com.words.Models.AccessToken;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.VolleyHelper;
import lombok.Getter;

public class SearchRepository {
    private static final String AUTHENTICATION_ENDPOINT = "https://accounts.spotify.com/api/token?grant_type=client_credentials";
    private static final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search?q=%s&type=track";

    private VolleyHelper volleyHelper;

    @Getter
    private AccessToken accessToken;


    public SearchRepository(){
        volleyHelper = VolleyHelper.getInstance();
    }

    public void search(String query, Consumer<ArrayList<Song>> callback){
        if (query == null || query.isEmpty()) {
            callback.accept(new ArrayList<>());
            return;
        }

        if (accessToken == null || !accessToken.isActive()) {
            authenticate(token -> {
                this.accessToken = token;
                this.searchInternal(query, callback);
            });
        } else {
            this.searchInternal(query, callback);
        }
    }

    private void searchInternal(String query, Consumer<ArrayList<Song>> callback) {
        String url = String.format(SEARCH_ENDPOINT, query.trim().replace(" ", "%20"));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    JSONArray tracks;
                    ArrayList<Song> searchResults = new ArrayList<>();
                    try {
                        tracks = response.getJSONObject("tracks").getJSONArray("items");
                    } catch (JSONException e) {
                        callback.accept(searchResults);
                        return;
                    }

                    for (int i = 0; i < tracks.length(); i++) {
                        try {
                            searchResults.add(toSong(tracks.getJSONObject(i)));
                        } catch (JSONException e) {
                            continue;
                        }
                    }

                    callback.accept(searchResults);
                },
                error -> {
                    Log.e("Volley", error.toString());
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", accessToken.getToken());
                return header;
            }
        };
        volleyHelper.queueRequest(request);
    }

    public void authenticate(Consumer<AccessToken> callback){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AUTHENTICATION_ENDPOINT, null,
                response -> {
                    try {
                        AccessToken token = new AccessToken(
                                response.getString("access_token"),
                                response.getString("token_type"),
                                response.getInt("expires_in")
                        );
                        callback.accept(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("Volley", error.toString());
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String authIdAndSecretBase64 = Base64.getEncoder().encodeToString((BuildConfig.spotifyKey + ":" + BuildConfig.spotifySecret).getBytes());
                headers.put("Authorization", "Basic " + authIdAndSecretBase64);
                return headers;
            }
        };
        volleyHelper.queueRequest(request);
    }

    private Song toSong(JSONObject jsonObject) throws JSONException{
        ArrayList<String> artistNames = new ArrayList<>();
        JSONArray artists = jsonObject.getJSONArray("artists");
        for (int i = 0; i < artists.length(); i++) {
            artistNames.add(artists.getJSONObject(i).getString("name"));
        }

        Song song = new Song(jsonObject.getString("name"), artistNames);
        return song;
    }
}
