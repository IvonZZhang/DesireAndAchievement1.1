package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DataModel {
    protected int id;
    protected String name;
    protected String coins;

    public DataModel(int id, String name, String coins) {
        this.id = id;
        this.name = name;
        this.coins = coins;
    }

    public DataModel(){}

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins){
        this.coins = coins;
    }
}
