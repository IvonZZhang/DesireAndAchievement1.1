package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.function.Consumer;

public class DBManager {

    public static void callServer(String url, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,response -> {},
                error -> System.out.println("failed to work"));
        queue.add(stringRequest);
    }

    public static void callServer(String url, Context context, final Consumer<String> responseConsumer) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                responseConsumer::accept, error -> System.out.println("failed to work"));
        queue.add(stringRequest);
    }
}
