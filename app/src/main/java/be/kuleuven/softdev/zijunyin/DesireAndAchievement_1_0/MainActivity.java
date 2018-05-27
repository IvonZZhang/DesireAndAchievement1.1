package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
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

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.HabitFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit.NewHabit;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.NewReward;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward.RewardFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.NewTodo;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo.TodoFragment;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.multiple_actions);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //添加底部导航栏
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        BottomNavigationItem habititem = new BottomNavigationItem(R.drawable.ic_habit_off, "Habit");
        BottomNavigationItem todoitem = new BottomNavigationItem(R.drawable.ic_todo_off, "Todo");
        BottomNavigationItem rewarditem = new BottomNavigationItem(R.drawable.ic_reward_off, "Reward");

        bottomNavigationBar
                .addItem(habititem)
                .addItem(todoitem)
                .addItem(rewarditem)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.collapse();
        onTabSelected(lastPosition);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        fab.collapse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //mHabitFragment = HabitFragment.newInstance("习惯");
        mHabitFragment = new HabitFragment();
        transaction.replace(R.id.mainDisplay, mHabitFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        lastPosition = position;
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mHabitFragment == null) {
                    //mHabitFragment = HabitFragment.newInstance();
                    mHabitFragment = new HabitFragment();
                }
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
        // 事务提交
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
