package be.kuleuven.softdev.zijunyin.getthecelan;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;


public class NewHabit extends AppCompatActivity {
    String[] Cycles =  {"Day","Week","Month","Year","None"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_habit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_habit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //监听左上角的返回箭头
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSaveHabit(View view){
        String habit_name;
        int coin;
        int times;
        String cycle;

        EditText editText1 =(EditText)findViewById(R.id.new_habit_name);
        habit_name = editText1.getText().toString();


        finish();
    }

    public void chooseCycle(View view){
        new AlertDialog.Builder(this)
                .setTitle("Choose cycle for new habit")
                .setSingleChoiceItems(Cycles, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NewHabit.this, "您已经选择了: " + which + ":" + Cycles[which],Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("OK", null)
                .show();
    }

}
