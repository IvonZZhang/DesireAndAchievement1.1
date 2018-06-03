package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.mNew;


public class HabitNew extends mNew {
    String[] Cycles =  {"Daily","Weekly","Monthly","None"};
    String cycle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        Toolbar toolbar = findViewById(R.id.new_habit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        cycle = "Daily";
    }

    public void finishSaveHabit(View view){
        String habit_name;
        int coins;
        int times;

        try {
            EditText editText1 = findViewById(R.id.new_habit_name);
            habit_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.habit_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());
            EditText editText3 = findViewById(R.id.times_each_cycle);
            times = Integer.parseInt(editText3.getText().toString());

            String url = "http://api.a17-sd603.studev.groept.be/add_habit/" +
                    habit_name + "/" +
                    cycle + "/" +
                    times + "/" +
                    "0" + "/" +
                    coins + "/" +
                    "0";
            DBManager.callServer(url, getBaseContext());
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }
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
