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
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class NewTodo extends AppCompatActivity{
    private TextView choosenDDL;
    private TextView choosenParent;
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
        choosenDDL = findViewById(R.id.choose_ddl);
        choosenDDL.setText(date);

        calendar = Calendar.getInstance();

        choosenParent = findViewById(R.id.parent);
        choosenParent.setText(R.string.none);

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
        //这里还要写怎么上传数据
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
                        choosenDDL.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
