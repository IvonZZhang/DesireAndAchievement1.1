package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DBManager;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

public class TodoAdapter extends RecyclerSwipeAdapter<TodoAdapter.ViewHolder>{
    private Context context;
    private ArrayList<TodoDataModel> todoArray;
    private String url;
    private int curCoins;

    public TodoAdapter(@NonNull Context context, ArrayList<TodoDataModel> todoArray) {
        this.context = context;
        this.todoArray = todoArray;
        curCoins = 0;
        Consumer<String> consumer = this::getCurCoins;
        url = "http://api.a17-sd603.studev.groept.be/get_coins";
        DBManager.callServer(url, context, consumer);
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_todo, parent, false);
        return new TodoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoAdapter.ViewHolder holder, final int position) {
        //Assign values
        holder.mTodo = todoArray.get(position);
        holder.todoName.setText(todoArray.get(position).getName());
        holder.deadline.setText(todoArray.get(position).getTodoDDL());
        holder.coinNumber.setText(String.format("+ %s", todoArray.get(position).getCoins()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(holder.deadline.getText().toString());
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.add(Calendar.DATE, -1);
        Date now = nowCalendar.getTime();
        if(convertedDate.before(now)){
            holder.coinNumber.setText(R.string.overdue);
//            holder.coinNumber.setTextColor(Color.WHITE);
//            holder.coinNumber.setBackgroundColor(Color.RED);
//            holder.coinNumber.setTextSize(16);
            holder.mTodo.setCoins("0");
        }

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
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
        holder.Complete.setOnClickListener(view -> {
            //update current coins
            int newCoins = curCoins + Integer.parseInt(todoArray.get(position).getCoins());
            curCoins = newCoins;

            //add new coins from achieved
            url = "http://api.a17-sd603.studev.groept.be/set_coins/" + newCoins;
            DBManager.callServer(url, context);
            Toast.makeText(view.getContext(), "AWESOME!", Toast.LENGTH_LONG).show();
            url = "http://api.a17-sd603.studev.groept.be/change_todo_delete_status/" + todoArray.get(position).getId();
            DBManager.callServer(url, context);

            mItemManger.removeShownLayouts(holder.swipeLayout);
            todoArray.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, todoArray.size());
            mItemManger.closeAllItems();

            holder.swipeLayout.close();
        });

        holder.Delete.setOnClickListener(v -> {
            url = "http://api.a17-sd603.studev.groept.be/change_todo_delete_status/" + todoArray.get(position).getId();
            DBManager.callServer(url, context);

            mItemManger.removeShownLayouts(holder.swipeLayout);
            todoArray.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, todoArray.size());
            mItemManger.closeAllItems();
            Toast.makeText(v.getContext(), "Deleted ", Toast.LENGTH_SHORT).show();
        });

        //apply ViewHolder
        mItemManger.bindView(holder.itemView, position);
    }

    private void getCurCoins(String response) {
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

    @Override
    public int getItemCount() {
        return todoArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;

        TextView todoName;
        TextView deadline;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Complete;
        TodoDataModel mTodo;

        ViewHolder(View view) {
            super(view);

            //mView = view;
            swipeLayout = itemView.findViewById(R.id.swipe);
            todoName = view.findViewById(R.id.todoName);
            deadline = view.findViewById(R.id.deadline);
            coinNumber = view.findViewById(R.id.coinNumber);
            Delete = itemView.findViewById(R.id.Delete);
            Complete = itemView.findViewById(R.id.Complete);
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
