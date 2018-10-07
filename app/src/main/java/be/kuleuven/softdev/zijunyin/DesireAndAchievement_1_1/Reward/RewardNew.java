package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.w3c.dom.Text;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.mNew;


public class RewardNew extends mNew{

    public static final String NEW_REWARD_NAME_REPLY = "NewRewardNameReply";
    public static final String NEW_REWARD_COINS_REPLY = "NewRewardCoinsReply";
    public static final String NEW_REWARD_REPEAT_REPLY = "NewRewardRepeatReply";

    private EditText mRewardName;
    private EditText mRewardCoins;

    private boolean isRepeated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward);

        Toolbar toolbar = findViewById(R.id.new_reward_toolbar);
        mRewardName = findViewById(R.id.new_reward_name);
        mRewardCoins = findViewById(R.id.reward_coin_number);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Switch isRepeatedSwitch = findViewById(R.id.is_repeated_switch);

        isRepeatedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isRepeated = isChecked;
        });
    }

    public void finishSaveReward(View view){

        Intent replyIntent = new Intent();
        if((TextUtils.isEmpty(mRewardName.getText()))||(TextUtils.isEmpty(mRewardCoins.getText()))){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }
        else{
            String rewardName = mRewardName.getText().toString();
            int rewardCoins = Integer.parseInt(mRewardCoins.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putString(NEW_REWARD_NAME_REPLY, rewardName);
            bundle.putInt(NEW_REWARD_COINS_REPLY, rewardCoins);
            bundle.putBoolean(NEW_REWARD_REPEAT_REPLY, isRepeated);

            replyIntent.putExtras(bundle);
            setResult(RESULT_OK, replyIntent);
            finish();
        }


        /*String reward_name;
        int coins;

        try {
            EditText editText1 = findViewById(R.id.new_reward_name);
            reward_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.reward_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());

//            String url = "http://api.a17-sd603.studev.groept.be/add_reward/" +
//                    reward_name + "/" +
//                    coins + "/" +
//                    isRepeatedIcon + "/" +
//                    "0";
//            DBManager.callServer(url, getBaseContext());

            String newRewardString = reward_name + "\n" +
                    coins + "\n" + isRepeated;

            Intent intent = new Intent();
            intent.putExtra(NEW_REWARD_REPLY, newRewardString);
            setResult(RESULT_OK, intent);
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }*/
    }
}
