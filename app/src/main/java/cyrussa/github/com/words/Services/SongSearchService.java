package cyrussa.github.com.words.Services;

import android.content.Context;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyrussa.github.com.words.Models.AccessToken;
import cyrussa.github.com.words.Models.Song;
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

    public void search(String query){
        if (query == null || query.isEmpty()){
            //something
        }

        String url = String.format(SEARCH_ENDPOINT, query.trim().replace(" ", "%20"));

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
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

    private Song toSong(JSONObject jsonObject) throws JSONException{
        // Incomplete
        Song song = new Song(jsonObject.getString("name"), jsonObject.getString("artists"));
        return null;
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


        String authIdAndSecretBase64 = "";
        try {
            InputStream is = context.getAssets().open("spotify_credentials.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            JSONObject credentials = new JSONObject();

            if(reader.hasNext()){
                credentials.put(reader.nextName(), reader.nextString());
            }
            authIdAndSecretBase64 = Base64.getEncoder().encodeToString((credentials.get("client_id") + ":" + credentials.get("client_secret")).getBytes());
        } catch (IOException | JSONException e) {

        }
        headers.put("Authorization", "Basic " + authIdAndSecretBase64);
        headers.put("Content-type", "application/x-www-form-urlencoded");
        headers.put("charset", "utf-8");
        return headers;
    }

    // Not currently working, need to get the structure of the request right
    public void authenticate() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(AUTHENTICATION_ENDPOINT).openConnection();

        conn.setRequestMethod("POST");
        getAuthHeaders().forEach(conn::setRequestProperty);
        conn.setDoOutput(true);

        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
            wr.write("grant_type=client_credentials".getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }

    // Alternate impl to authentiate
    public void authenticate_VOLLEY(){
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("grant_type", "client_credentials");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AUTHENTICATION_ENDPOINT,
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

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() {
                return "grant_type=client_credentials".getBytes();
            }
        };
        volleyHelper.queueRequest(request);
    }
}
