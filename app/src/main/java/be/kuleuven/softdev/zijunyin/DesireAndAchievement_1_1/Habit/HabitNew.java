package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.mNew;


public class HabitNew extends mNew {

    public static final String NEW_HABIT_NAME_REPLY = "NewHabitNameReply";
    public static final String NEW_HABIT_COINS_REPLY = "NewHabitCoinsReply";
    public static final String NEW_HABIT_CYCLE_REPLY = "NewHabitCycleReply";
    public static final String NEW_HABIT_TIMES_REPLY = "NewHabitTimesReply";

    private EditText mHabitName;
    private EditText mRewardCoins;
    private EditText mTimesPerCycle;

    String[] Cycles =  {"Daily","Weekly","Monthly","None"};
    String cycle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        Toolbar toolbar = findViewById(R.id.new_habit_toolbar);
        mHabitName = findViewById(R.id.new_habit_name);
        mRewardCoins = findViewById(R.id.habit_coin_number);
        mTimesPerCycle = findViewById(R.id.times_each_cycle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cycle = "Daily";

    }

    public void finishSaveHabit(View view){
        Intent replyIntent = new Intent();
        if((TextUtils.isEmpty(mHabitName.getText()))||(TextUtils.isEmpty(mRewardCoins.getText()))
                ||(TextUtils.isEmpty(mTimesPerCycle.getText()))){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }
        else{
            String habitName = mHabitName.getText().toString();
            int rewardCoins = Integer.parseInt(mRewardCoins.getText().toString());
            int timesPerCycle = Integer.parseInt(mTimesPerCycle.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putString(NEW_HABIT_NAME_REPLY, habitName);
            bundle.putInt(NEW_HABIT_COINS_REPLY, rewardCoins);
            bundle.putInt(NEW_HABIT_TIMES_REPLY, timesPerCycle);
            bundle.putString(NEW_HABIT_CYCLE_REPLY, cycle);

            replyIntent.putExtras(bundle);
            setResult(RESULT_OK, replyIntent);
            finish();
        }

        /*
        String habit_name;
        int coins;
        int times;*/

       /* try {
            EditText editText1 = findViewById(R.id.new_habit_name);
            habit_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.habit_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());
            EditText editText3 = findViewById(R.id.times_each_cycle);
            times = Integer.parseInt(editText3.getText().toString());

//            String url = "http://api.a17-sd603.studev.groept.be/add_habit/" +
//                    habit_name + "/" +
//                    cycle + "/" +
//                    times + "/" +
//                    "0" + "/" +
//                    coins + "/" +
//                    "0";
            //DBManager.callServer(url, getBaseContext());

            String newHabitString = habit_name + "\n" +
                        cycle + "\n" +
                        times + "\n" +
                        "0" + "\n" +
                        coins + "\n" +
                        "0";

            Intent intent = new Intent();
            intent.putExtra(NEW_HABIT_REPLY, newHabitString);
            setResult(RESULT_OK, intent);
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }*/
    }

    public void chooseCycle(View view){
        AlertDialog di = new AlertDialog.Builder(this)
                .setTitle("Choose cycle for new habit")
                .setSingleChoiceItems(Cycles, 0,
                        (dialog, which) -> {
                            TextView new_cycle = findViewById(R.id.your_cycle);
                            new_cycle.setText(Cycles[which]);
                            cycle = Cycles[which];
                            dialog.dismiss();
                        }
                )
                .show();
        di.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }
}
