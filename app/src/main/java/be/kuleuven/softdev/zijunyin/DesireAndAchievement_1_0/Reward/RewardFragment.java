package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.app.Fragment;
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

import java.util.ArrayList;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

public class RewardFragment extends Fragment{
    private RecyclerView rewardList;
    private ArrayList<RewardDataModel> rewardArray;

    public RewardFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        String url_space = "http://api.a17-sd603.studev.groept.be/reward_convert_space";
        DBManager.callServer(url_space, getContext());

        String url ="http://api.a17-sd603.studev.groept.be/testReward";
        DBManager.callServer(url, getContext(), this::parseRewardData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_list, container, false);

        rewardList = view.findViewById(R.id.rewardRecyclerView);
        rewardList.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardArray = new ArrayList<>();

        /*if(rewardArray.isEmpty()){
            rewardList.setVisibility(View.GONE);
        }
        else{
            rewardList.setVisibility(View.VISIBLE);
        }*/
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

    public void parseRewardData(String response) {
        try{
            rewardArray = new ArrayList<>();
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curRewardId = jObj.getInt("idReward");
                String curRewardName = jObj.getString("RewardName");
                String curIsRepeated = jObj.getString("isRepeated");
                String curRewardCoins = jObj.getString("ConsumedCoins");
                rewardArray.add(
                        new RewardDataModel(curRewardId, curRewardName, curIsRepeated, "-" + curRewardCoins)
                );
            }
            RewardAdapter rewardAdapter = new RewardAdapter(getContext(), rewardArray);
            rewardAdapter.setMode(Attributes.Mode.Single);
            rewardList.setAdapter(rewardAdapter);
        }
        catch (JSONException e){
            System.out.println( e );
        }
    }
}
