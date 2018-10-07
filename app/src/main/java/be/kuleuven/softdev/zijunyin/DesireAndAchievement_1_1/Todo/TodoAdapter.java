package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.content.Context;
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

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitViewModel;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.User;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserDao;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TodoAdapter extends RecyclerSwipeAdapter<TodoAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;

//        Toodo toodo;
        TextView todoName;
        TextView deadline;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Complete;

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

    private final LayoutInflater mInflator;
    private List<Todo> todos;
    private TodoViewModel todoViewModel;
    private UserViewModel userViewModel;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    TodoAdapter(@NonNull Context context) {
        todos = new ArrayList<>();
        mInflator = LayoutInflater.from(context);
    }

    void setViewModel(UserViewModel userViewModel, TodoViewModel todoViewModel){
        this.userViewModel = userViewModel;
        this.todoViewModel = todoViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator
                .inflate(R.layout.fragment_todo, parent, false);
        return new TodoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoAdapter.ViewHolder holder, final int position) {

        if(todos != null){
            Todo currentTodo = todos.get(position);
            holder.todoName.setText(currentTodo.getTodoName());
            holder.deadline.setText("DDL: " + dateFormat.format(currentTodo.getDeadline()));
            holder.coinNumber.setText(String.format("+ %s", currentTodo.getRewardCoins()));
        }
        else {
            // TODO: 2018/7/16 show something when nothing is in the Todo List
        }


        checkOverdue(holder, position);

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        createSwipeListener(holder);

        setCompleteOnClickListener(holder, position);
        setDeleteOnClickListener(holder, position);

        //apply ViewHolder
        mItemManger.bindView(holder.itemView, position);
    }

    private void setDeleteOnClickListener(ViewHolder holder, int position) {
        holder.Delete.setOnClickListener(v -> {
//            url = "http://api.a17-sd603.studev.groept.be/change_todo_delete_status/" + todoArray.get(position).getId();
//            DBManager.callServer(url, context);

            removeATodoFromList(holder, position);
            Toast.makeText(v.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
        });
    }

    private void removeATodoFromList(ViewHolder holder, int position) {
        mItemManger.removeShownLayouts(holder.swipeLayout);
//        todos.remove(position);
//        todoDao.delete(holder.todo);
        todoViewModel.delete(todos.get(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, todos.size());
        mItemManger.closeAllItems();
    }

    private void setCompleteOnClickListener(ViewHolder holder, int position) {
        holder.Complete.setOnClickListener(view -> {
            //update current coins
            Todo currentTodo = todos.get(position);
            int curCoins = 0;
            try{
                curCoins = userViewModel.getCoins();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            userViewModel.updateCoins(curCoins + currentTodo.getRewardCoins());

            //add new coins from achieved
//            url = "http://api.a17-sd603.studev.groept.be/set_coins/" + newCoins;
//            DBManager.callServer(url, context);
            Toast.makeText(view.getContext(), R.string.Awesome, Toast.LENGTH_LONG).show();
//            url = "http://api.a17-sd603.studev.groept.be/change_todo_delete_status/" + todoArray.get(position).getId();
//            DBManager.callServer(url, context);

            removeATodoFromList(holder, position);
//            todoViewModel.delete(todos.get(position));

            holder.swipeLayout.close();
        });
    }

    private void createSwipeListener(ViewHolder holder) {
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
    }

    void setTodo(List<Todo> todos){
        this.todos = todos;
        notifyDataSetChanged();
    }

    private void checkOverdue(ViewHolder holder, int position) {

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
            todoViewModel.setRewardCoins(0, todos.get(position).getIdTodo());
        }
    }

//    private void getCurCoins(String response) {
//        try{
//            JSONArray jArr = new JSONArray(response);
//            for(int i = 0; i < jArr.length(); i++){
//                JSONObject jObj = jArr.getJSONObject( i );
//                curCoins = jObj.getInt("Coins");
//                System.out.println(jObj.getInt("Coins"));
//            }
//        }
//        catch(JSONException e){
//            System.out.println(e);
//        }
//    }

    @Override
    public int getItemCount() {
        return todos.size();
    }



    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}
