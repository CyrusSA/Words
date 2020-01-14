package cyrussa.github.com.words.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class VolleyHelper {
    private static VolleyHelper instance;
    private final RequestQueue requestQueue;

    private VolleyHelper(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public synchronized static void init(Context context){
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        instance = new VolleyHelper(context);
    }

    public static VolleyHelper getInstance(){
        if(instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public void queueRequest(Request request){
        requestQueue.add(request);
    }
}
