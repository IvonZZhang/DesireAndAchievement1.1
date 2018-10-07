package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

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

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;

import static android.app.Activity.RESULT_OK;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardNew.NEW_REWARD_COINS_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardNew.NEW_REWARD_NAME_REPLY;
import static be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardNew.NEW_REWARD_REPEAT_REPLY;

public class RewardFragment extends Fragment {

    public static final int NEW_REWARD_FRAGMENT_REQUEST_CODE = 3;

    private RewardAdapter rewardAdapter;
    private RewardViewModel rewardViewModel;
    private Context context;

    public RewardFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        rewardAdapter = new RewardAdapter(context);
    }

    @Override
    public void onResume() {
        super.onResume();

//        String url_space = "http://api.a17-sd603.studev.groept.be/reward_convert_space";
//        DBManager.callServer(url_space, getContext());
//
//        String url ="http://api.a17-sd603.studev.groept.be/testReward";
//        DBManager.callServer(url, getContext(), this::parseRewardData);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_list, container, false);

        RecyclerView rewardList = view.findViewById(R.id.rewardRecyclerView);
        rewardList.setLayoutManager(new LinearLayoutManager(context));
        rewardList.setAdapter(rewardAdapter);

        rewardViewModel = ViewModelProviders.of(this).get(RewardViewModel.class);
        rewardViewModel.getAllRewards().observe(this, rewardAdapter::setReward);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        rewardAdapter.setViewModel(userViewModel, rewardViewModel);

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

    public void createNewItem(Context context){
        Intent intent = new Intent(context, RewardNew.class);
        startActivityForResult(intent, NEW_REWARD_FRAGMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_REWARD_FRAGMENT_REQUEST_CODE && resultCode == RESULT_OK){
//            Reward reward = createRewardFromString(data.getStringExtra(RewardNew.NEW_REWARD_REPLY));
            Bundle bundle = data.getExtras();
            Reward reward = createRewardFromBundle(bundle);
            rewardViewModel.insert(reward);
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    private Reward createRewardFromBundle(Bundle bundle) {
        return new Reward(bundle.getString(NEW_REWARD_NAME_REPLY), bundle.getInt(NEW_REWARD_COINS_REPLY),
                bundle.getBoolean(NEW_REWARD_REPEAT_REPLY));
    }

    /*private Reward createRewardFromString(String stringExtra) {
        String[] rewardArguments = stringExtra.split("\n");
        boolean isRepeated = false;
        if(rewardArguments[2].equals("true")){
            isRepeated = true;
        }
        return new Reward(rewardArguments[0], Integer.parseInt(rewardArguments[1]), isRepeated);
    }*/

    /*public void parseRewardData(String response) {
        try{
            rewardArray = new ArrayList<>();
            JSONArray jArr = new JSONArray(response);//response is String but a JSONArray needed, so add it into try-catch
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                int curRewardId = jObj.getInt("idReward");
                String curRewardName = jObj.getString("RewardName");
                String curIsRepeated = jObj.getString("isRepeatedIcon");
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
    }*/
}
