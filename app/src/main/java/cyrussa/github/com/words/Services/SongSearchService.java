package cyrussa.github.com.words.Services;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyrussa.github.com.words.Models.AccessToken;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.SearchDisplayActivity;
import lombok.Getter;

public class SongSearchService {
    private static final String AUTHENTICATION_ENDPOINT = "https://accounts.spotify.com/api/token/";
    private static final String SEARCH_ENDPOINT = "https://api.spotify.com/v1/search/q=%s&type=track";

    private VolleyHelper volleyHelper;

    @Getter
    private AccessToken accessToken;

    private Context context;

    public SongSearchService(Context context){
        volleyHelper = VolleyHelper.getInstance();
        this.context = context;
    }

    public void search(AppCompatActivity activity, String query){
        ArrayList<Song> mockSearchResults = new ArrayList<>(Arrays.asList(new Song("Hello", "Adele"), new Song("Bye", "Ya Boi"), new Song("Bydwade", "Ya Bodwi"), new Song("Bye", "Ya Boi")));
        Intent intent = new Intent(activity, SearchDisplayActivity.class);
        intent.putExtra("searchResults", mockSearchResults);
        activity.startActivity(intent);
//        if (query == null || query.isEmpty()){
//            //something
//        }
//
//        String url = String.format(SEARCH_ENDPOINT, query.trim().replace(" ", "%20"));
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                response -> {
//                    List<Song> searchResults = new ArrayList<>();
//                    try {
//                        for (int i = 0; i < response.length(); i++){
//                            searchResults.add(toSong(response.getJSONObject(i)));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    //start search activity
//                },
//                error -> {
//                    Log.e("Volley", error.toString());
//                }
//        ){
//            @Override
//            public Map<String, String> getHeaders() {
//                return getSearchAuthHeader();
//            }
//        };
//        volleyHelper.queueRequest(request);
    }


}
