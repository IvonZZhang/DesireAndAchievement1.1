package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;

import static android.app.Activity.RESULT_OK;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitNew.NEW_HABIT_COINS_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitNew.NEW_HABIT_CYCLE_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitNew.NEW_HABIT_NAME_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitNew.NEW_HABIT_TIMES_REPLY;

public class HabitFragment extends Fragment {

    public static final int NEW_HABIT_FRAGMENT_REQUEST_CODE = 1;

    private HabitAdapter habitAdapter;
    private HabitViewModel habitViewModel;
    private Context context;
    private SimpleDateFormat simpleDateFormat;

    public HabitFragment() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        habitAdapter = new HabitAdapter(context);
    }

    @Override
    public void onResume() {
        super.onResume();

//        String url_space = "http://api.a17-sd603.studev.groept.be/habit_convert_space";
//        DBManager.callServer(url_space, getContext());
//
//        String url ="http://api.a17-sd603.studev.groept.be/testHabit";
//        DBManager.callServer(url, getContext(), this::parseHabitData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);

        RecyclerView habitRecyclerView = view.findViewById(R.id.habitRecyclerView);
        habitRecyclerView.setAdapter(habitAdapter);
        habitRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        habitViewModel = ViewModelProviders.of(this).get(HabitViewModel.class);
        habitViewModel.getAllHabits().observe(this, habitAdapter::setHabit);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        habitAdapter.setViewModel(userViewModel, habitViewModel);


        //habitArray = new ArrayList<>();



        /*if(habitArray.isEmpty()){
            habitRecyclerView.setVisibility(View.GONE);
        }
        else{
            habitRecyclerView.setVisibility(View.VISIBLE);
        }*/

        habitRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    public void createNewItem(Context context){
        Intent intent = new Intent(context, HabitNew.class);
        startActivityForResult(intent, NEW_HABIT_FRAGMENT_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_HABIT_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK) {

//            Habit habit = createHabitFromString(data.getStringExtra(HabitNew.NEW_HABIT_REPLY));
            Bundle bundle = data.getExtras();
            Habit habit = createHabitFromBundle(bundle);
            habitViewModel.insert(habit);
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    private Habit createHabitFromBundle(Bundle bundle) {
        return new Habit(bundle.getString(NEW_HABIT_NAME_REPLY),
                        bundle.getString(NEW_HABIT_CYCLE_REPLY).equals("None") ? "∞" : bundle.getString(NEW_HABIT_CYCLE_REPLY),
                        bundle.getInt(NEW_HABIT_TIMES_REPLY), 0, bundle.getInt(NEW_HABIT_COINS_REPLY),
                        bundle.getString(NEW_HABIT_CYCLE_REPLY) == "Weekly" ? getLastMonday() : getTodayDateWithZeroTime());
    }

    private Date getLastMonday(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        while(calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()){
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    private Date getTodayDateWithZeroTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

//    private Habit createHabitFromString(String stringExtra) {
//        String[] habitArguments = stringExtra.split("\n");
//        return new Habit(habitArguments[0], habitArguments[1],
//                Integer.parseInt(habitArguments[2]), Integer.parseInt(habitArguments[3]),
//                Integer.parseInt(habitArguments[4]));
//    }
/*
    public void removeHabit(Habit habit){
        habitViewModel.delete(habit);
    }

    public void increaseTimesDone(int hid){
        habitViewModel.increaseTimesDone(hid);
    }

    public void setCoins(int coins){
        AppDatabase.getDatabase(getActivity()).userDao().setCoins();
    }*/

//    public void parseHabitData(String response) {
//        try{
//            habitArray = new ArrayList<>();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            JSONArray jArr = new JSONArray(response);
//            for(int i = 0; i < jArr.length(); i++){
//                JSONObject jObj = jArr.getJSONObject( i );
//                int curHabitId = jObj.getInt("idHabit");
//                String curHabitName = jObj.getString("HabitName");
//                String curHabitCycle = jObj.getString("HabitCycle");
//                String curTimesPerCycle = jObj.getString("TimesPerCycle");
//                String curTimesDone = jObj.getString("TimesDone");
//                String curRewardCoins = jObj.getString("RewardCoins");
//                String curCycleStartDate = jObj.getString("CycleStartDate");
//                if(curCycleStartDate.isEmpty()) {
//                    curCycleStartDate = dateFormat.format(Calendar.getInstance().getTime());
//                }
//                habitArray.add(
//                        new HabitDataModel(curHabitId, curHabitName, curHabitCycle, curTimesDone, curTimesPerCycle,
//                                curRewardCoins, curCycleStartDate)
//                );
//            }
//            HabitAdapter habitAdapter = new HabitAdapter(getContext(), habitArray);
//            habitAdapter.setMode(Attributes.Mode.Single);
//            habitList.setAdapter(habitAdapter);
//        }
//        catch (JSONException e) {
//            System.out.println(e);
//        }
//    }

//    public void refreshHabits(ArrayList<HabitDataModel> habits) {
//        this.habitArray.clear();
//        this.habitArray.addAll(habits);
//        notifyDataSetChanged();
//    }
}
