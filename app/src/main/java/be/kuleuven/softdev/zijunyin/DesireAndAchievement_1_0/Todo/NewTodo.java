package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class NewTodo extends AppCompatActivity{
    private TextView chosenDDL;
    private String chosenDDL_string;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;

    private Calendar calendar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_todo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new java.util.Date());
        chosenDDL = findViewById(R.id.choose_ddl);
        chosenDDL.setText(date);
        chosenDDL_string = date;

        calendar = Calendar.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSaveTodo(View view){
        String todo_name;
        int coins;

        try {
            EditText editText1 = findViewById(R.id.new_todo_name);
            todo_name = editText1.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.todo_coin_number);
            coins = Integer.parseInt(editText2.getText().toString());

            String url = "http://api.a17-sd603.studev.groept.be/add_todo/" +
                    todo_name + "/" +
                    chosenDDL_string + "/" +
                    coins + "/" +
                    "0";
            DBManager.callServer(url, getBaseContext());
            finish();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Please fill in all conditions!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void showDialog(View view) {
        new DatePickerDialog(
                this,
                // set listener
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        chosenDDL.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        chosenDDL_string = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    }
                }
                , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )
                .show();
    }

}
