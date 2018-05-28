package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.util.ArrayList;
import java.util.function.Consumer;

public class HabitFragment extends Fragment {

    private RecyclerView habitList;
    private ArrayList<HabitDataModel> habitArray;

    public HabitFragment() {
    }

/*TODO: refresh the list every time it has changed*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);

        habitList = view.findViewById(R.id.habitRecyclerView);
        habitList.setLayoutManager(new LinearLayoutManager(getContext()));
        habitArray = new ArrayList<>();

        String url ="http://api.a17-sd603.studev.groept.be/testHabit";

        Consumer<String> consumer = this::parseHabitData;
        DBManager.callServer(url, getContext(), consumer);

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
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curHabitId = jObj.getInt("idHabit");
                String curHabitName = jObj.getString("HabitName");
                String curHabitCycle = jObj.getString("HabitCycle");
                String curTimesPerCycle = jObj.getString("TimesPerCycle");
                String curTimesDone = jObj.getString("TimesDone");
                String curRewardCoins = jObj.getString("RewardCoins");
                if(jObj.getInt("isDeleted") == 0){
                    habitArray.add(
                            new HabitDataModel(curHabitId, curHabitName, curHabitCycle + " " + curTimesDone + "/" + curTimesPerCycle, "+" + curRewardCoins)
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
