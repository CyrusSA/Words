package cyrussa.github.com.words.Services.Api;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyrussa.github.com.words.BuildConfig;
import cyrussa.github.com.words.Models.AccessToken;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.SongSearchService;
import cyrussa.github.com.words.Services.VolleyHelper;
import lombok.Getter;

public class SpotifySongSearchService implements SongSearchService {
    private static final String AUTHENTICATION_ENDPOINT = "https://accounts.spotify.com/api/token/?grant_type=client_credentials";
    private static final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search/q=%s&type=track";

    private VolleyHelper volleyHelper;

    @Getter
    private AccessToken accessToken;

    private Context context;

    public SpotifySongSearchService(Context context){
        volleyHelper = VolleyHelper.getInstance();
        this.context = context;
    }

    public void search(AppCompatActivity activity, String query){
        String url = String.format(SEARCH_ENDPOINT, query.trim().replace(" ", "%20"));

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Song> searchResults = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++){
                            searchResults.add(toSong(response.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //start search activity
                },
                error -> {
                    Log.e("Volley", error.toString());
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                return getSearchAuthHeader();
            }
        };
        volleyHelper.queueRequest(request);
    }

    private Map<String, String> getSearchAuthHeader(){
        Map<String, String> header = new HashMap<>();
        if (!accessToken.isActive()){
            //authenticate();
        }

        while (!accessToken.isActive()){ }

        header.put("Authorization", accessToken.getToken());
        return header;
    }

    private Map<String, String> getAuthHeaders(){
        Map<String, String> headers = new HashMap<>();
        String authIdAndSecretBase64 = Base64.getEncoder().encodeToString((BuildConfig.spotifyKey + ":" + BuildConfig.spotifySecret).getBytes());
        headers.put("Authorization", "Basic " + authIdAndSecretBase64);
        return headers;
    }

    public void authenticate(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AUTHENTICATION_ENDPOINT, null,
                response -> {
                    try {
                        AccessToken token = new AccessToken(
                                response.getString("access_token"),
                                response.getString("token_type"),
                                response.getInt("expires_in")
                        );
                        this.accessToken = token;
                        System.out.println(token.getToken());
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
                return getAuthHeaders();
            }
        };
        volleyHelper.queueRequest(request);
    }

    private Song toSong(JSONObject jsonObject) throws JSONException{
        Song song = new Song(jsonObject.getString("name"), jsonObject.getString("artists"));
        return null;
    }
}
