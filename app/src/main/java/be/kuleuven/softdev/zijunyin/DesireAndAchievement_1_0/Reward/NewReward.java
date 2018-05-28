package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class NewReward extends AppCompatActivity{
    private Switch is_repeated_switch;
    private int isRepeated = 0 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_reward_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        is_repeated_switch = (Switch)findViewById(R.id.is_repeated_switch);

//        final SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        is_repeated_switch.setChecked(sharedPreferences.getBoolean("your_key", true)); //true default

        is_repeated_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    isRepeated = 1;
                }
                else
                {
                    isRepeated = 0;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //监听左上角的返回箭头
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSaveReward(View view){
        String reward_name;
        int coins;

        EditText editText1 = findViewById(R.id.new_reward_name);
        reward_name = editText1.getText().toString();
        EditText editText2 =(EditText)findViewById(R.id.reward_coin_number);
        coins = Integer.parseInt(editText2.getText().toString());

        String url = "http://api.a17-sd603.studev.groept.be/add_reward/"+
                reward_name + "/" +
                coins + "/" +
                isRepeated + "/" +
                "0";
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        System.out.println(response);

                        //parse JSON to String
                        //String result = "";
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                System.out.println("failed to work");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        finish();
    }
}
