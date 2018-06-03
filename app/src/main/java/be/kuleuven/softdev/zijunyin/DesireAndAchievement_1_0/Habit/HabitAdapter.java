package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

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
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HabitAdapter extends RecyclerSwipeAdapter<HabitAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HabitDataModel> habitArray;
    private int curCoins;
    private String firstDayOfWeek;
    private String url;

    HabitAdapter(@NonNull Context context, ArrayList<HabitDataModel> habitArray) {
        this.context = context;
        this.habitArray = habitArray;
        firstDayOfWeek = "Monday";
        curCoins = 0;
        Consumer<String> consumer = this::getCurCoins;
        url = "http://api.a17-sd603.studev.groept.be/get_coins";
        DBManager.callServer(url, context, consumer);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_habit, parent, false);
        return new HabitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HabitAdapter.ViewHolder holder, final int position) {

        updateTimesdone(position);

        //Assign values
        holder.mHabit = habitArray.get(position);
        holder.habitName.setText(habitArray.get(position).getName());
        holder.CycleAndTimes.setText(String.format("%s %s/%s", habitArray.get(position).getHabitCycle(), habitArray.get(position).getTimesDone(), habitArray.get(position).getTimesPerCycle()));
        holder.coinNumber.setText(String.format("+%s", habitArray.get(position).getCoins()));

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        createSwipeListener(holder);

        setCompleteOnClickListener(holder, position);
        setDeleteOnClickListener(holder, position);

        mItemManger.bindView(holder.itemView, position);
    }

    private void setDeleteOnClickListener(@NonNull ViewHolder holder, int position) {
        holder.Delete.setOnClickListener(view -> {
            url = "http://api.a17-sd603.studev.groept.be/change_habit_delete_status/" + habitArray.get(position).getId();
            DBManager.callServer(url, context);

            mItemManger.removeShownLayouts(holder.swipeLayout);
            habitArray.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, habitArray.size());
            mItemManger.closeAllItems();
            Toast.makeText(view.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
        });
    }

    private void setCompleteOnClickListener(@NonNull ViewHolder holder, int position) {
        holder.Complete.setOnClickListener(view -> {
            url = "http://api.a17-sd603.studev.groept.be/increase_timesdone/" + habitArray.get(position).getId();
            DBManager.callServer(url, context);

            int curTimesDone = Integer.parseInt(habitArray.get(position).getTimesDone());
            curTimesDone = curTimesDone + 1;
            habitArray.get(position).setTimesDone(String.valueOf(curTimesDone));


            holder.CycleAndTimes.setText(String.format("%s %s/%s", habitArray.get(position).getHabitCycle(), habitArray.get(position).getTimesDone(), habitArray.get(position).getTimesPerCycle()));

            int newCoins = curCoins + Integer.parseInt(habitArray.get(position).getCoins());
            curCoins = newCoins;

            //add new coins from achieved
            url = "http://api.a17-sd603.studev.groept.be/set_coins/" + newCoins;
            DBManager.callServer(url, context);

            Toast.makeText(view.getContext(), R.string.YouDidIt, Toast.LENGTH_LONG).show();
            //holder.
            holder.swipeLayout.close();
        });
    }

    private void createSwipeListener(@NonNull ViewHolder holder) {
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

    private void updateTimesdone(int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone timeZone = TimeZone.getDefault();
        System.out.println(timeZone);
        Calendar currentDCalendar = Calendar.getInstance();
        Calendar convertedCalendar = Calendar.getInstance();
        currentDCalendar.setTimeZone(timeZone);
        convertedCalendar.setTimeZone(timeZone);
        switch (habitArray.get(position).getHabitCycle().charAt(0)){
            case 'D':{
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(timeZone);
                Date currentDate = calendar.getTime();
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(habitArray.get(position).getCycleStartDate());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||(currentDate.after(convertedDate))){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(Calendar.getInstance().getTime())
                            + "/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);
                }

                break;
            }
            case 'W':{
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(habitArray.get(position).getCycleStartDate());
                    convertedCalendar.setTime(convertedDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||(daysBetween(currentDCalendar, convertedCalendar) > 7.0)){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(getTheLatestStartDay())
                            + "/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);
                }

                break;
            }
            case 'M':{
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(habitArray.get(position).getCycleStartDate());
                    convertedCalendar.setTime(convertedDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||((currentDCalendar.get(Calendar.YEAR) > convertedCalendar.get(Calendar.YEAR))||
                        ((currentDCalendar.get(Calendar.YEAR) == convertedCalendar.get(Calendar.YEAR))
                                &&(currentDCalendar.get(Calendar.MONTH) > convertedCalendar.get(Calendar.MONTH))))){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(Calendar.getInstance().getTime())
                            + "/" + habitArray.get(position).getId();
                    DBManager.callServer(url, context);
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    private Date getTheLatestStartDay() {
        Calendar calendar = Calendar.getInstance();
        url = "http://api.a17-sd603.studev.groept.be/get_first_day_of_week";
        DBManager.callServer(url, context, this::getFirstDay);
        if(firstDayOfWeek.equals("Monday")) {
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
        }
        else{
            calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        }
        while(calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()){
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    private long daysBetween(Calendar currentDCalendar, Calendar convertedCalendar) {
        long end = currentDCalendar.getTimeInMillis();
        long start = convertedCalendar.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    @Override
    public int getItemCount() {
        return habitArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;

        TextView habitName;
        TextView CycleAndTimes;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Complete;
        HabitDataModel mHabit;

        ViewHolder(View view) {
            super(view);

            swipeLayout = itemView.findViewById(R.id.swipe);
            habitName = view.findViewById(R.id.habitName);
            CycleAndTimes = view.findViewById(R.id.deadline);
            coinNumber = view.findViewById(R.id.coinNumber);
            Delete = itemView.findViewById(R.id.Delete);
            Complete = itemView.findViewById(R.id.Complete);
        }
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private void getCurCoins(String response) {
        try{
            JSONArray jArr = new JSONArray(response);
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject( i );
                curCoins = jObj.getInt("Coins");
            }
        }
        catch(JSONException e){
            System.out.println(e);
        }
    }

    private void getFirstDay(String response) {
        try{
            JSONArray jArr = new JSONArray(response);
            for(int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                if (!jObj.getString("FirstDay").isEmpty()) {
                    firstDayOfWeek = jObj.getString("FirstDay");
                }
            }
        }
        catch(JSONException e){
            System.out.println(e);
        }
    }
}