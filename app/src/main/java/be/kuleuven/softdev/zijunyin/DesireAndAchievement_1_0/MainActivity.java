package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.HabitFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.NewHabit;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.NewReward;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.RewardFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.NewTodo;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.TodoFragment;

import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R.id.nav_header_main;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private HabitFragment mHabitFragment;
    private TodoFragment mTodoFragment;
    private RewardFragment mRewardFragment;
    private FloatingActionsMenu fab;
    private int lastPosition;
    private String[] languages = {"English", "Chinese"};
    private String chosen_language;
    private String[] pages = {"Habit","Todo","Reward"};
    private String chosen_default_page;
    private String[] weekdays = {"Monday","Sunday"};
    private String chosen_first_day;
    private TextView current_coin_number;
    private String curCoinNumber;
    private DrawerLayout drawer;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chosen_default_page = "Reward";
        updateDefaultPage();
        System.out.println(chosen_default_page);
        chosen_language = "English";

        fab = findViewById(R.id.multiple_actions);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //updateCoinNumber();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateCoinNumber();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //add left hand drawer view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        current_coin_number = headerView.findViewById(R.id.current_coin_number);
        updateCoinNumber();

        //add bottom navigation bar
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        BottomNavigationItem habititem = new BottomNavigationItem(R.drawable.ic_habit_off, "Habit");
        BottomNavigationItem todoitem = new BottomNavigationItem(R.drawable.ic_todo_off, "Todo");
        BottomNavigationItem rewarditem = new BottomNavigationItem(R.drawable.ic_reward_off, "Reward");

        bottomNavigationBar
                .addItem(habititem)
                .addItem(todoitem)
                .addItem(rewarditem)
                .initialise();
        System.out.println(chosen_default_page);
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        int chosen_default_page_int = Arrays.asList(pages).indexOf(chosen_default_page);
//        onTabSelectedRefresh(chosen_default_page_int);
//
//    }

    @Override
    // TODO: 2018/5/28 每次拉开抽屉数字都刷新
    public boolean onPrepareOptionsMenu(Menu menu){
        if (drawer != null && drawer.isDrawerOpen(nav_header_main)) {
            updateCoinNumber();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateCoinNumber(){
        String url = "http://api.a17-sd603.studev.groept.be/get_coin_umber";
        Consumer<String> consumer = this::parseCoinData;
        DBManager.callServer(url,getBaseContext(),consumer);
    }

    public void parseCoinData(String response) {
        try{
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            JSONObject jObj = jArr.getJSONObject(0);
            curCoinNumber = jObj.getString("Coins");
            current_coin_number.setText(curCoinNumber);
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

    public void updateDefaultPage(){
        String url = "http://api.a17-sd603.studev.groept.be/get_default_page";
        System.out.println(chosen_default_page+"222");
        Consumer<String> consumer = this::parseDefaultPage;
        System.out.println(chosen_default_page+"111");
        DBManager.callServer(url,getBaseContext(),consumer);
        System.out.println(chosen_default_page);
    }

    public void parseDefaultPage(String response) {
        try{
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            JSONObject jObj = jArr.getJSONObject(0);
            chosen_default_page = jObj.getString("DefaultPage");
            System.out.println(chosen_default_page + "han shu li");
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
            new AlertDialog.Builder(this)
                    .setTitle("Choose language")
                    .setSingleChoiceItems(languages, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    chosen_language = languages[which];
                                }
                            }
                    )
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), chosen_language,
                                            Toast.LENGTH_LONG).show();
                                    // TODO: 2018/5/28  这里写按下保存语言选择后的操作。使用已经存下选项的String chosen_language
                                }
                    })
                    .show();
        }
        else if (id == R.id.nav_default_page) {
            new AlertDialog.Builder(this)
                    .setTitle("Choose the default page")
                    .setSingleChoiceItems(pages, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    chosen_default_page = pages[which];
                                }
                            }
                    )
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), chosen_default_page,
                                            Toast.LENGTH_LONG).show();
                                    String url = "http://api.a17-sd603.studev.groept.be/set_default_page/" +
                                            chosen_default_page;
                                    DBManager.callServer(url, getBaseContext());
                                }
                    })
                    .show();
        }
        else if (id == R.id.nav_first_weekday) {
            new AlertDialog.Builder(this)
                    .setTitle("Choose the first day of week")
                    .setSingleChoiceItems(weekdays, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    chosen_first_day = weekdays[which];
                                }
                            }
                    )
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), chosen_first_day,
                                            Toast.LENGTH_LONG).show();
                                    String url = "http://api.a17-sd603.studev.groept.be/set_first_day/" +
                                            chosen_first_day;
                                    DBManager.callServer(url, getBaseContext());
                                }
                            })
                    .show();
        }
        else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        lastPosition = position;
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //open fragment
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mHabitFragment == null) {
                    //mHabitFragment = HabitFragment.newInstance();
                    mHabitFragment = new HabitFragment();
                }
                transaction.replace(R.id.mainDisplay, mHabitFragment);
                toolbar.setBackgroundColor(getResources().getColor(R.color.greenDark));
                break;
            case 1:
                if (mTodoFragment == null) {
                    //mTodoFragment = TodoFragment.newInstance("待办");
                    mTodoFragment = new TodoFragment();
                }
                transaction.replace(R.id.mainDisplay, mTodoFragment);
                toolbar.setBackgroundColor(getResources().getColor(R.color.blueDark));
                break;
            case 2:
                if (mRewardFragment == null) {
                    //mRewardFragment = RewardFragment.newInstance("奖励");
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

    public void onTabSelectedRefresh(int position) {
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //open fragment
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                //if (mHabitFragment == null) {
                    //mHabitFragment = HabitFragment.newInstance();
                    mHabitFragment = new HabitFragment();
                //}
                transaction.replace(R.id.mainDisplay, mHabitFragment);
                break;
            case 1:
                if (mTodoFragment == null) {
                    //mTodoFragment = TodoFragment.newInstance("待办");
                    mTodoFragment = new TodoFragment();
                }
                transaction.replace(R.id.mainDisplay, mTodoFragment);
                break;
            case 2:
                if (mRewardFragment == null) {
                    //mRewardFragment = RewardFragment.newInstance("奖励");
                    mRewardFragment = new RewardFragment();
                }
                transaction.replace(R.id.mainDisplay, mRewardFragment);
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
}

