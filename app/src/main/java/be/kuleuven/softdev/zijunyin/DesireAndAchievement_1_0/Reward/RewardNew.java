package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.mNew;


public class RewardNew extends mNew{
    private Switch isRepeatedSwitch;
    private int isRepeated = 0 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward);

        Toolbar toolbar = findViewById(R.id.new_reward_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        isRepeatedSwitch = findViewById(R.id.is_repeated_switch);

        isRepeatedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
