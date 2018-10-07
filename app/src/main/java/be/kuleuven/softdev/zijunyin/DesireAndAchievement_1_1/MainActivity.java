package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1;

import android.Manifest;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Locale;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitNew;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardNew;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoNew;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoFragment;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserListAdapter;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationBar.OnTabSelectedListener{

    private static final int NEW_HABIT_FRAGMENT_REQUEST_CODE = 1;
    private static final int NEW_TODO_FRAGMENT_REQUEST_CODE = 2;
    private static final int NEW_REWARD_FRAGMENT_REQUEST_CODE = 3;
    private String TAG = MainActivity.class.getSimpleName();
    private HabitFragment mHabitFragment;
    private TodoFragment mTodoFragment;
    private RewardFragment mRewardFragment;
    private FloatingActionsMenu fab;
    private String[] languages = {"English", "中文"};
    private int chosenLanguage = 0;    //0->English, 1->Chinese
    private String[] weekdays = {"Monday","Sunday"};
    private int chosenFirstDay = 0;   //0->Monday, 1->Sunday
    private TextView currentCoinNumber;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    BottomNavigationItem habitItem;
    BottomNavigationItem todoItem;
    BottomNavigationItem rewardItem;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.multiple_actions);

        drawer = findViewById(R.id.drawer_layout);
        setDrawerToggle();

        mHabitFragment = new HabitFragment();
        mTodoFragment = new TodoFragment();
        mRewardFragment = new RewardFragment();

        //add left hand drawer view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        currentCoinNumber = headerView.findViewById(R.id.current_coin_number);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        UserListAdapter userListAdapter = new UserListAdapter(this);
        userViewModel.getTheUser().observe(this, userListAdapter::setUsers);
//        updateData();

        //add bottom navigation bar
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        habitItem = new BottomNavigationItem(R.drawable.ic_habit_off, getString(R.string.Habit));
        todoItem = new BottomNavigationItem(R.drawable.ic_todo_off, getString(R.string.Todo));
        rewardItem = new BottomNavigationItem(R.drawable.ic_reward_off, getString(R.string.Reward));

        bottomNavigationBar
                .addItem(habitItem)
                .addItem(todoItem)
                .addItem(rewardItem)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
        /*onTabSelected(1);
        onTabSelected(2);
        onTabSelected(0);*/

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
        currentCoinNumber.setText(userViewModel.getCoins() + "");
    }

    /*public void updateData(){
        String url = "http://api.a17-sd603.studev.groept.be/get_user_data";
        DBManager.callServer(url,getBaseContext(), this::parseData);
    }

    public void parseData(String response){
        try{
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            JSONObject jObj = jArr.getJSONObject(0);

            currentCoinNumber.setText(jObj.getString("Coins"));

            if(jObj.getString("FirstDay").equals("Monday")){
                chosenFirstDay = 0;
            }
            else {
                chosenFirstDay = 1;
            }

            if(jObj.getString("Language").equals("English")){
                chosenLanguage = 0;
            }
            else {
                chosenLanguage = 1;
            }
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }*/

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        else if (id == R.id.export_db) {
            exportDB();
        }
        else if (id == R.id.import_db) {
            importDB();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showWeekdayDialog() {
//        updateData();
        String[] weekdaysToShow = {getString(R.string.Monday), getString(R.string.Sunday)};
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.chooseFirstDayOfWeek))
                .setSingleChoiceItems(weekdaysToShow, chosenFirstDay,
                        (dialog, which) -> chosenFirstDay = which
                )
                .setPositiveButton(getString(R.string.ok),
                        (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), weekdaysToShow[chosenFirstDay],
                                    Toast.LENGTH_LONG).show();
//                            String url = "http://api.a17-sd603.studev.groept.be/set_first_day/" +
//                                    weekdays[chosenFirstDay];
//                            DBManager.callServer(url, getBaseContext());
                        })
                .show();
    }

    private void showLanguageDialog() {
//        updateData();
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.ChooseLanguage))
                .setSingleChoiceItems(languages, chosenLanguage,
                        (dialog, which) -> chosenLanguage = which
                )
                .setPositiveButton(getString(R.string.ok),
                        (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), languages[chosenLanguage],
                                    Toast.LENGTH_LONG).show();
                            if(languages[chosenLanguage].equals("English")){
                                setLocale("en");
                            }
                            else{
                                setLocale("zh");
                            }
                            /*String url = "http://api.a17-sd603.studev.groept.be/set_language/" +
                                    languages[chosenLanguage];
                            DBManager.callServer(url, getBaseContext());*/

                        })
                .show();
    }

    /**
     * set default fragment
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainDisplay, mHabitFragment);
        toolbar.setBackgroundColor(getResources().getColor(R.color.greenDark));

        transaction.commit();
        /*FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.mainDisplay, mTodoFragment);
        transaction1.commit();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.mainDisplay, mRewardFragment);
        transaction2.commit();
        transaction.commit();*/
    }

    @Override
    public void onTabSelected(int position) {
        fab.collapse();
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        //open fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
        Intent intent = new Intent(this, HabitNew.class);
//        startActivity(intent);
//        startActivityForResult(intent, NEW_HABIT_FRAGMENT_REQUEST_CODE);
        onTabSelected(0);
        mHabitFragment.createNewItem(this);
    }

    public void onClickNewTodo(View view) {
        Intent intent = new Intent(this, TodoNew.class);
//        startActivity(intent);
//        startActivityForResult(intent, NEW_TODO_FRAGMENT_REQUEST_CODE);
        onTabSelected(1);
        /*View mView = findViewById(R.id.bottom_navigation_bar);
        mView.callOnClick();*/
        mTodoFragment.createNewItem(this);
    }

    public void onClickNewReward(View view) {
        Intent intent = new Intent(this, RewardNew.class);
//        startActivity(intent);
//        startActivityForResult(intent, NEW_REWARD_FRAGMENT_REQUEST_CODE);
        onTabSelected(2);
        mRewardFragment.createNewItem(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    public boolean exportDB(){

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            AppDatabase.getDatabase(this).close();

            File db = getDatabasePath("D&A_database");

            File db2 = new File(Environment.getExternalStorageDirectory().getPath(), "DBExport");

            try {
                FileUtils.copyFileToDirectory(db, db2);
                Toast.makeText(this, "Export DB successfully!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("EXPORT_DB", e.toString());
            }
            return true;
        } else {
            Toast.makeText(this, "Please allow access to write your storage", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
            return false;
        }

    }

    public boolean importDB(){
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            AppDatabase.getDatabase(this).close();

            File db = new File(Environment.getExternalStorageDirectory().getPath() + "/DBExport", "D&A_Database");

            File db2 = getDatabasePath("D&A_database");

            try {
                FileUtils.copyFile(db, db2);
                Toast.makeText(this, "Import DB successfully!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            } catch (Exception e) {
                Log.e("RESTORE_DB", e.toString());
            }
            return true;
        } else {
            Toast.makeText(this, "Please allow access to read your storage", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 0);
            return false;
        }
    }
}

