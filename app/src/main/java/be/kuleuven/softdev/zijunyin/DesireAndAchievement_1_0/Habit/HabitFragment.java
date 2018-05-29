package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;

public class HabitFragment extends Fragment {

    private RecyclerView habitList;
    private ArrayList<HabitDataModel> habitArray;

    @Override
    public void onResume() {
        super.onResume();
        String url ="http://api.a17-sd603.studev.groept.be/testHabit";

        Consumer<String> consumer = this::parseHabitData;
        DBManager.callServer(url, getContext(), consumer);
    }

    public HabitFragment() {
    }

    public static HabitFragment newInstance(){
        HabitFragment habitFragment = new HabitFragment();
        return habitFragment;
    }

    public HabitFragment getInstance(){
        return this;
    }
/*TODO: refresh the list every time it has changed*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);

        habitList = view.findViewById(R.id.habitRecyclerView);
        habitList.setLayoutManager(new LinearLayoutManager(getContext()));
        habitArray = new ArrayList<>();

//        String url ="http://api.a17-sd603.studev.groept.be/testHabit";
//
//        Consumer<String> consumer = this::parseHabitData;
//        DBManager.callServer(url, getContext(), consumer);

        /*if(habitArray.isEmpty()){
            habitList.setVisibility(View.GONE);
        }
        else{
            habitList.setVisibility(View.VISIBLE);
        }*/

        habitList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    public void parseHabitData(String response) {
        try{
            habitArray = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            JSONArray jArr = new JSONArray(response);
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curHabitId = jObj.getInt("idHabit");
                String curHabitName = jObj.getString("HabitName");
                String curHabitCycle = jObj.getString("HabitCycle");
                String curTimesPerCycle = jObj.getString("TimesPerCycle");
                String curTimesDone = jObj.getString("TimesDone");
                String curRewardCoins = jObj.getString("RewardCoins");
                String curCycleStartDate = jObj.getString("CycleStartDate");
                if(curCycleStartDate.isEmpty()) {
                    curCycleStartDate = dateFormat.format(Calendar.getInstance().getTime());
                }
                if(jObj.getInt("isDeleted") == 0){
                    habitArray.add(
                            new HabitDataModel(curHabitId, curHabitName, curHabitCycle, curTimesDone, curTimesPerCycle,
                                    curRewardCoins, curCycleStartDate)
                    );
                }
            }
            HabitAdapter habitAdapter = new HabitAdapter(getContext(), habitArray);
            habitAdapter.setMode(Attributes.Mode.Single);
            habitList.setAdapter(habitAdapter);
        }
        catch (JSONException e) {
            System.out.println(e);
        }
    }

//    public void refreshHabits(ArrayList<HabitDataModel> habits) {
//        this.habitArray.clear();
//        this.habitArray.addAll(habits);
//        notifyDataSetChanged();
//    }
}
