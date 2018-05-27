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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class NewTodo extends AppCompatActivity{
    private TextView chosenDDL;
    private TextView chosenParent;
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

        calendar = Calendar.getInstance();

        chosenParent = findViewById(R.id.parent);
        chosenParent.setText(R.string.none);

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


        EditText editText1 = findViewById(R.id.new_todo_name);
        todo_name = editText1.getText().toString();
        EditText editText2 =(EditText)findViewById(R.id.todo_coin_number);
        coins = Integer.parseInt(editText2.getText().toString());


        String url = "http://api.a17-sd603.studev.groept.be/add_todo/"+
                todo_name + "/" +
                chosenDDL_string + "/" +
                coins + "/" +
                "0";

        //final ArrayList<HabitDataModel> habitArray = new ArrayList<HabitDataModel>();
        //HabitDataModel habitDataModel = new HabitDataModel();

        // Instantiate the RequestQueue.
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ddl_layout:
                showDialog();
                break;
            case R.id.choose_parent:
                chooseParent();
                break;
        }
    }

    private void showDialog() {
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

//                this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                //monthOfYear 得到的月份会减1所以我们要加1
//                String time = String.valueOf(year) + "　"
//                            + String.valueOf(monthOfYear + 1) + "  "
//                            + Integer.toString(dayOfMonth);
//                Log.d("Test", time);
//            }
//        },
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)

//        datePickerDialog.show();
//        //自动弹出键盘问题解决
//        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public void chooseParent(){
        new AlertDialog.Builder(this)
                .setTitle("Choose todo parent")
                .setSingleChoiceItems(new String[] {"程序逻辑寻找现有todo列表"}, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("Save", null)
                .show();
    }
}
