package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.mNew;


public class TodoNew extends mNew{
    private TextView chosenDDL;
    private String chosenDDLString;

    private Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        Toolbar toolbar = findViewById(R.id.new_todo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new java.util.Date());
        chosenDDL = findViewById(R.id.choose_ddl);
        chosenDDL.setText(date);
        chosenDDLString = date;

        calendar = Calendar.getInstance();
    }

    public void finishSaveTodo(View view){
        String todo_name;
        int coins;

        try {
            EditText editText1 = findViewById(R.id.new_todo_name);
            todo_name = editText1.getText().toString();
            EditText editText2 = findViewById(R.id.todo_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());

            String url = "http://api.a17-sd603.studev.groept.be/add_todo/" +
                    todo_name + "/" +
                    chosenDDLString + "/" +
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