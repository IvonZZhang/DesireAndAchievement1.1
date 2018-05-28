package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

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

    /*public static int callServer(String url, Context context, final Supplier<Integer> responseSupplier) {
        //int presentCoins;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int presentCoins = responseSupplier.get();
                    }
                }, error -> System.out.println("failed to work"));
        queue.add(stringRequest);
        return presentCoins;
    }*/


}
