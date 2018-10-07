package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.mNew;


public class TodoNew extends mNew{
    public static final String NEW_TODO_NAME_REPLY = "NewTodoNameReply";
    public static final String NEW_TODO_COINS_REPLY = "NewTodoCoinsReply";
    public static final String NEW_TODO_DDL_REPLY = "NewTodoDDLReply";

    private EditText mTodoName;
    private EditText mRewardCoins;
    private TextView chosenDDL;

    private String chosenDDLString;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        Toolbar toolbar = findViewById(R.id.new_todo_toolbar);
        mTodoName = findViewById(R.id.new_todo_name);
        mRewardCoins = findViewById(R.id.todo_coin_number);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        chosenDDLString = sdf.format(new java.util.Date());
        chosenDDL = findViewById(R.id.choose_ddl);
        chosenDDL.setText(chosenDDLString);

        calendar = Calendar.getInstance();
    }

    public void finishSaveTodo(View view){

        Intent replyIntent = new Intent();
        if((TextUtils.isEmpty(mTodoName.getText()))||(TextUtils.isEmpty(mRewardCoins.getText()))){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }
        else{
            String todoName = mTodoName.getText().toString();
            int rewardCoins = Integer.parseInt(mRewardCoins.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putString(NEW_TODO_NAME_REPLY, todoName);
            bundle.putInt(NEW_TODO_COINS_REPLY, rewardCoins);
            bundle.putString(NEW_TODO_DDL_REPLY, chosenDDLString);

            replyIntent.putExtras(bundle);
            setResult(RESULT_OK, replyIntent);
            finish();
        }


        /*String todo_name;
        int coins;

        try {
            EditText editText1 = findViewById(R.id.new_todo_name);
            todo_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.todo_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());

//            String url = "http://api.a17-sd603.studev.groept.be/add_todo/" +
//                    todo_name + "/" +
//                    chosenDDLString + "/" +
//                    coins + "/" +
//                    "0";
//            DBManager.callServer(url, getBaseContext());

            String newTodoString = todo_name + "\n" +
                    chosenDDLString +"\n" + coins;

            Intent intent = new Intent();
            intent.putExtra(NEW_TODO_REPLY, newTodoString);
            setResult(RESULT_OK, intent);

            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.WarmFillAll,
                    Toast.LENGTH_LONG).show();
        }*/
    }

    public void showDateDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, (view1, year, monthOfYear, dayOfMonth) -> {
            chosenDDL.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            chosenDDLString = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //solve keyboard conflict
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
