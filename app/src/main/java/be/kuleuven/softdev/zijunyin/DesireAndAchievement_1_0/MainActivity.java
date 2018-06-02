package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.function.Consumer;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.HabitFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.NewHabit;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.NewReward;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.RewardFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.NewTodo;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.TodoFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationBar.OnTabSelectedListener{

    private String TAG = MainActivity.class.getSimpleName();
    private HabitFragment mHabitFragment;
    private TodoFragment mTodoFragment;
    private RewardFragment mRewardFragment;
    private FloatingActionsMenu fab;
    private String[] languages = {"English", "中文"};
    private int chosen_language = 0;    //0->English, 1->Chinese
    private String[] weekdays = {"Monday","Sunday"};
    private int chosen_first_day = 0;   //0->Monday, 1->Sunday
    private TextView current_coin_number;
    private DrawerLayout drawer;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.multiple_actions);

        drawer = findViewById(R.id.drawer_layout);
        setDrawerToggle();

        //add left hand drawer view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        current_coin_number = headerView.findViewById(R.id.current_coin_number);
        updateData();

        //add bottom navigation bar
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        BottomNavigationItem habititem = new BottomNavigationItem(R.drawable.ic_habit_off, getString(R.string.Habit));
        BottomNavigationItem todoitem = new BottomNavigationItem(R.drawable.ic_todo_off, getString(R.string.Todo));
        BottomNavigationItem rewarditem = new BottomNavigationItem(R.drawable.ic_reward_off, getString(R.string.Reward));

        bottomNavigationBar
                .addItem(habititem)
                .addItem(todoitem)
                .addItem(rewarditem)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
    }

    private void setDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateData();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void updateData(){
        String url = "http://api.a17-sd603.studev.groept.be/get_user_data";
        Consumer<String> consumer = this::parseData;
        DBManager.callServer(url,getBaseContext(),consumer);
    }

    public void parseData(String response){
        try{
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            JSONObject jObj = jArr.getJSONObject(0);

            current_coin_number.setText(jObj.getString("Coins"));

            if(jObj.getString("FirstDay").equals("Monday")){
                chosen_first_day = 0;
            }
            else {
                chosen_first_day = 1;
            }

            if(jObj.getString("Language").equals("English")){
                chosen_language = 0;
            }
            else {
                chosen_language = 1;
            }
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //close fab menu when main activity resume
        fab.collapse();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_manual) {
            Intent intent = new Intent(this, UserManual.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_language) {
            showLanguageDialog();
        }
        else if (id == R.id.nav_first_weekday) {
            showWeekdayDialog();
        }
        else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showWeekdayDialog() {
        updateData();
        String[] weekdaysToShow = {getString(R.string.Monday), getString(R.string.Sunday)};
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.chooseFirstDayOfWeek))
                .setSingleChoiceItems(weekdaysToShow, chosen_first_day,
                        (dialog, which) -> chosen_first_day = which
                )
                .setPositiveButton(getString(R.string.ok),
                        (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), weekdaysToShow[chosen_first_day],
                                    Toast.LENGTH_LONG).show();
                            String url = "http://api.a17-sd603.studev.groept.be/set_first_day/" +
                                    weekdays[chosen_first_day];
                            DBManager.callServer(url, getBaseContext());
                        })
                .show();
    }

    private void showLanguageDialog() {
        updateData();
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.ChooseLanguage))
                .setSingleChoiceItems(languages, chosen_language,
                        (dialog, which) -> chosen_language = which
                )
                .setPositiveButton(getString(R.string.ok),
                        (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), languages[chosen_language],
                                    Toast.LENGTH_LONG).show();
                            if(languages[chosen_language].equals("English")){
                                setLocale("en");
                            }
                            else{
                                setLocale("zh");
                            }
                            String url = "http://api.a17-sd603.studev.groept.be/set_language/" +
                                    languages[chosen_language];
                            DBManager.callServer(url, getBaseContext());

                        })
                .show();
    }

    /**
     * set default fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
                mHabitFragment = new HabitFragment();
                transaction.replace(R.id.mainDisplay, mHabitFragment);
        toolbar.setBackgroundColor(getResources().getColor(R.color.greenDark));

        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        fab.collapse();
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //open fragment
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mHabitFragment == null) {
                    mHabitFragment = new HabitFragment();
                }
                transaction.replace(R.id.mainDisplay, mHabitFragment);
                toolbar.setBackgroundColor(getResources().getColor(R.color.greenDark));
                break;
            case 1:
                if (mTodoFragment == null) {
                    mTodoFragment = new TodoFragment();
                }
                transaction.replace(R.id.mainDisplay, mTodoFragment);
                toolbar.setBackgroundColor(getResources().getColor(R.color.blueDark));
                break;
            case 2:
                if (mRewardFragment == null) {
                    mRewardFragment = new RewardFragment();
                }
                transaction.replace(R.id.mainDisplay, mRewardFragment);
                toolbar.setBackgroundColor(getResources().getColor(R.color.orangeDark));
                break;
            default:
                break;
        }
        // commit fragment
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }

    public void onClickNewHabit(View view) {
        Intent intent = new Intent(this, NewHabit.class);
        startActivity(intent);
    }

    public void onClickNewTodo(View view) {
        Intent intent = new Intent(this, NewTodo.class);
        startActivity(intent);
    }

    public void onClickNewReward(View view) {
        Intent intent = new Intent(this, NewReward.class);
        startActivity(intent);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}

