//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//          Buddha protect my code from bugs.
//          God bless Buddha.


package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.util.ArrayList;
import java.util.function.Consumer;

public class RewardAdapter extends RecyclerSwipeAdapter<RewardAdapter.ViewHolder>{
    private Context context;
    private ArrayList<RewardDataModel> rewardArray;
    private int curCoins;
    private String url;

    public RewardAdapter(@NonNull Context context, ArrayList<RewardDataModel> rewardArray) {
        this.context = context;
        this.rewardArray = rewardArray;
        curCoins = 0;
        Consumer<String> consumer = this::getCurCoins;
        url = "http://api.a17-sd603.studev.groept.be/get_coins";
        DBManager.callServer(url, context, consumer);
    }

    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reward, parent, false);
        return new RewardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RewardAdapter.ViewHolder holder, final int position) {
        //Assign values
        holder.mReward = rewardArray.get(position);
        holder.rewardName.setText(rewardArray.get(position).getName());

        if(rewardArray.get(position).isRepeated().equals("1")){
            holder.isRepeated.setImageResource(R.drawable.ic_repeat);
        }
        else{
            holder.isRepeated.setImageResource(R.drawable.ic_one_time);
        }

        holder.coinNumber.setText(rewardArray.get(position).getCoins());

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //dari kiri
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        //dari kanan
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        //Create swipe listener
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {}

            @Override
            public void onOpen(SwipeLayout layout) {}

            @Override
            public void onStartClose(SwipeLayout layout) {}

            @Override
            public void onClose(SwipeLayout layout) {}

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {}

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {}
        });

        //Set On Click Listener to menu elements

        holder.Complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCoins = curCoins + Integer.parseInt(rewardArray.get(position).getCoins());
                curCoins = newCoins;

                //add new coins from achieved
                url = "http://api.a17-sd603.studev.groept.be/set_coins/" + newCoins;
                DBManager.callServer(url, context);
                Toast.makeText(view.getContext(), "", Toast.LENGTH_SHORT).show();

                holder.swipeLayout.close();
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://api.a17-sd603.studev.groept.be/change_reward_delete_status/" + rewardArray.get(position).getId();
                DBManager.callServer(url, context);

                mItemManger.removeShownLayouts(holder.swipeLayout);
                rewardArray.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rewardArray.size());
                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "Deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        //apply ViewHolder
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return rewardArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //public View mView;
        SwipeLayout swipeLayout;

        TextView rewardName;
        ImageView isRepeated;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Complete;
        RewardDataModel mReward;

        ViewHolder(View view) {
            super(view);

            //mView = view;
            swipeLayout = itemView.findViewById(R.id.swipe);
            rewardName = view.findViewById(R.id.RewardName);
            isRepeated = view.findViewById(R.id.is_repeat);
            coinNumber = view.findViewById(R.id.coinNumber);
            Delete = itemView.findViewById(R.id.Delete);
            Complete = itemView.findViewById(R.id.Complete);
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void getCurCoins(String response) {
        try{
            JSONArray jArr = new JSONArray(response);
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                curCoins = jObj.getInt("Coins");
                System.out.println(jObj.getInt("Coins"));
            }
        }
        catch(JSONException e){
            System.out.println(e);
        }
    }

}
