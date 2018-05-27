package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.app.Fragment;
import android.os.Bundle;
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

import java.util.ArrayList;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class RewardFragment extends Fragment{
    private RecyclerView rewardList;
    private ArrayList<RewardDataModel> rewardArray;

    public RewardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_list, container, false);

        rewardList = view.findViewById(R.id.rewardRecyclerView);
        rewardList.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardArray = new ArrayList<>();

        String url ="http://api.a17-sd603.studev.groept.be/testReward";

        //final ArrayList<RewardDataModel> rewardArray = new ArrayList<RewardDataModel>();
        //RewardDataModel habitDataModel = new RewardDataModel();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

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

                        try{
                            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
                            for(int i = 0; i < jArr.length(); i++){
                                JSONObject jObj = jArr.getJSONObject( i );
                                String curRewardName = jObj.getString("RewardName");
                                //String curHabitCycle = jObj.getString("HabitCycle");
                                //String curTimesPerCycle = jObj.getString("TimesPerCycle");
                                //String curTimesDone = jObj.getString("TimesDone");
                                String curIsRepeated = jObj.getString("isRepeated");
                                String curRewardCoins = jObj.getString("ConsumedCoins");
                                //result += curHabitName + " " + curHabitCycle + curTimesPerCycle + curTimesDone + curRewardCoins;//###String can also use +=
                                //if(jObj.getInt("isDeleted") == 0){
                                rewardArray.add(
                                        new RewardDataModel(curRewardName, curIsRepeated, "-" + curRewardCoins)
                                );
                                //}
                            }
                            RewardAdapter rewardAdapter = new RewardAdapter(getContext(), rewardArray);
                            rewardAdapter.setMode(Attributes.Mode.Single);
                            rewardList.setAdapter(rewardAdapter);
                        }
                        catch (JSONException e){
                            System.out.println( e );
                        }

//                        ListView rewardList = (ListView) findViewById(R.id.HabitListView);


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

        /*if(rewardArray.isEmpty()){
            rewardList.setVisibility(View.GONE);
        }
        else{
            rewardList.setVisibility(View.VISIBLE);
        }*/

/*
        System.out.println("12345678900987654321");
        System.out.println(rewardArray);
        rewardArray.add(
                new RewardDataModel("1", "2", "3")
        );
        System.out.println("added, added, added, added");
        System.out.println(rewardArray);*/

        rewardList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
}
