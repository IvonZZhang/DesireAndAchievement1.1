package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;


public class NewReward extends AppCompatActivity{
    private Switch is_repeated_switch;
    private int isRepeated = 0 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward);

        Toolbar toolbar = findViewById(R.id.new_reward_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        is_repeated_switch = findViewById(R.id.is_repeated_switch);

        is_repeated_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                isRepeated = 1;
            }
            else
            {
                isRepeated = 0;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSaveReward(View view){
        String reward_name;
        int coins;

        try {
            EditText editText1 = findViewById(R.id.new_reward_name);
            reward_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.reward_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());

            String url = "http://api.a17-sd603.studev.groept.be/add_reward/" +
                    reward_name + "/" +
                    coins + "/" +
                    isRepeated + "/" +
                    "0";
            DBManager.callServer(url, getBaseContext());
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }
    }
}
