package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

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

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.R;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class HabitAdapter extends RecyclerSwipeAdapter<HabitAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;

//        Habit habit;
        TextView habitName;
        TextView CycleAndTimes;
        TextView coinNumber;

        ImageButton Delete;
        ImageButton Complete;

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

    private final LayoutInflater mInflator;
    private List<Habit> habits;
    private HabitViewModel habitViewModel;
    private UserViewModel userViewModel;

    HabitAdapter(@NonNull Context context) {
        mInflator = LayoutInflater.from(context);
        habits = new ArrayList<Habit>();
    }

    void setViewModel(UserViewModel userViewModel, HabitViewModel habitViewModel){
        this.userViewModel = userViewModel;
        this.habitViewModel = habitViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator
                .inflate(R.layout.fragment_habit, parent, false);
        return new HabitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HabitAdapter.ViewHolder holder, final int position) {

       if(habits != null){
           Habit currentHabit = habits.get(position);
           holder.habitName.setText(currentHabit.getHabitName());
           holder.CycleAndTimes.setText(String.format("%s %s/%s", currentHabit.getHabitCycle(), currentHabit.getTimesDone(),
                   currentHabit.getTimesPerCycle()));

           clearTimesDone(currentHabit);

           holder.coinNumber.setText(String.format("+%s", currentHabit.getRewardCoins()));
        } else {
            // Covers the case of data not being ready yet.
            //holder.wordItemView.setText("No Word");
            // TODO: 2018/7/3 Something might have to be added here.
        }

        //Create swipe menu
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wraper));

        createSwipeListener(holder);

        setCompleteOnClickListener(holder, position);
        setDeleteOnClickListener(holder, position);

        mItemManger.bindView(holder.itemView, position);
    }

    private void clearTimesDone(Habit currentHabit) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone timeZone = TimeZone.getDefault();
           /*Calendar currentCalendar = Calendar.getInstance();
           Calendar cycleStartCalendar = Calendar.getInstance();
           currentCalendar.setTimeZone(timeZone);
           cycleStartCalendar.setTimeZone(timeZone);*/

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        Date currentDate = calendar.getTime();
        Date cycleStartDate = habitViewModel.getCycleStartDate(currentHabit.getIdHabit());

        switch (currentHabit.getHabitCycle().charAt(0)){
            case 'D':{
                // TODO: 2018/10/5 BUG when open the app next time on exactly the same day next year
                if((cycleStartDate == null)||
                        (currentDate.getDate() != cycleStartDate.getDate())||
                        (currentDate.getMonth() != cycleStartDate.getMonth())){
                    clearTimesDoneAndSetNewStartDate(currentHabit, currentDate);
                }

                break;
            }
            case 'W':{
                //cycleStartDate is already a Monday
                if( (cycleStartDate == null) ||
                        (currentDate.after(getNextMonday(cycleStartDate))) ){
                        //(daysBetween(currentDate, cycleStartDate) >= 7.0) ){
                        /*//opne on a Monday after within the same month
                        ( (currentDate.getDay() == Calendar.MONDAY) && (currentDate.getDate() != cycleStartDate.getDate()) )||
                        //open on a Monday after with different month
                        ( (currentDate.getDay() == Calendar.MONDAY) && (currentDate.getMonth() > cycleStartDate.getMonth()) )||
                        //open on a non-Monday after within the same month
                        ( (currentDate.getDay() != Calendar.MONDAY) && (currentDate.getDate() - cycleStartDate.getDate() >= 7) )||
                        //open on a non-Monday after with different month
                        ( (currentDate.getDay() != Calendar.MONDAY) && (currentDate.getMonth() > cycleStartDate.getMonth()) )){*/
                    clearTimesDoneAndSetNewStartDate(currentHabit, currentDate);
                }

                break;
            }
            case 'M':{
                // TODO: 2018/10/6 BUG when open the app next time exactly the same month with different year. 
                if( (cycleStartDate == null)||
                        ( currentDate.getMonth() != cycleStartDate.getMonth() )||
                        ( currentDate.getYear() > cycleStartDate.getYear() ) ){
                     clearTimesDoneAndSetNewStartDate(currentHabit, currentDate);
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    private void clearTimesDoneAndSetNewStartDate(Habit currentHabit, Date currentDate) {
        habitViewModel.setTimesDone(0, currentHabit.getIdHabit());
        habitViewModel.setCycleStartDate(currentDate, currentHabit.getIdHabit());
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private void setDeleteOnClickListener(@NonNull ViewHolder holder, int position) {
        holder.Delete.setOnClickListener(view -> {


            mItemManger.removeShownLayouts(holder.swipeLayout);
            //habitArray.remove(position);
//            habits.remove(position);
//            habitDao.delete(holder.habit);
            // TODO: 2018/9/16 加了ViewModel, 不知道对不对。
            habitViewModel.delete(habits.get(position));
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,  habits.size());
            mItemManger.closeAllItems();
            Toast.makeText(view.getContext(), R.string.delete, Toast.LENGTH_SHORT).show();
        });
    }

    private void setCompleteOnClickListener(@NonNull ViewHolder holder, int position) {
        holder.Complete.setOnClickListener(view -> {
            Habit currentHabit = habits.get(position);
            habitViewModel.increaseTimesDone(currentHabit);

            int curTimesDone = currentHabit.getTimesDone();
            curTimesDone = curTimesDone + 1;
            habitViewModel.setTimesDone(curTimesDone, currentHabit.getIdHabit());

            holder.CycleAndTimes.setText(String.format("%s %s/%s", currentHabit.getHabitCycle(),
                    currentHabit.getTimesDone(), currentHabit.getTimesPerCycle()));

            int curCoins = 0;
            try{
                curCoins = userViewModel.getCoins();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            userViewModel.updateCoins(curCoins + currentHabit.getRewardCoins());

            //add new coins from achieved
//            url = "http://api.a17-sd603.studev.groept.be/set_coins/" + newCoins;
//            DBManager.callServer(url, context);
//            userViewModel.setCoins(curCoins);


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

    void setHabit(List<Habit> habits){
        this.habits = habits;
        notifyDataSetChanged();
    }

    private Date getNextMonday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        while(calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()){
            calendar.add(Calendar.DATE, 1);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    /*private void updateTimesdone(int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone timeZone = TimeZone.getDefault();
        System.out.println(timeZone);
        Calendar currentDCalendar = Calendar.getInstance();
        Calendar convertedCalendar = Calendar.getInstance();
        currentDCalendar.setTimeZone(timeZone);
        convertedCalendar.setTimeZone(timeZone);
        switch (current.getHabitCycle().charAt(0)){
            case 'D':{
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(timeZone);
                Date currentDate = calendar.getTime();
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(current.getCycleStartDate());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||(currentDate.after(convertedDate))){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + current.getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(Calendar.getInstance().getTime())
                            + "/" + current.getId();
                    DBManager.callServer(url, context);
                }

                break;
            }
            case 'W':{
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(current.getCycleStartDate());
                    convertedCalendar.setTime(convertedDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||(daysBetween(currentDCalendar, convertedCalendar) > 7.0)){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + current.getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(getTheLatestStartDay())
                            + "/" + current.getId();
                    DBManager.callServer(url, context);
                }

                break;
            }
            case 'M':{
                Date convertedDate;
                try {
                    convertedDate = dateFormat.parse(current.getCycleStartDate());
                    convertedCalendar.setTime(convertedDate);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    convertedDate = null;
                }

                if((convertedDate == null)||((currentDCalendar.get(Calendar.YEAR) > convertedCalendar.get(Calendar.YEAR))||
                        ((currentDCalendar.get(Calendar.YEAR) == convertedCalendar.get(Calendar.YEAR))
                                &&(currentDCalendar.get(Calendar.MONTH) > convertedCalendar.get(Calendar.MONTH))))){
                    url = "http://api.a17-sd603.studev.groept.be/clear_timesdone/" + current.getId();
                    DBManager.callServer(url, context);

                    url = "http://api.a17-sd603.studev.groept.be/set_cycle_start_date/"
                            + dateFormat.format(Calendar.getInstance().getTime())
                            + "/" + current.getId();
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
    }*/

    /*private long daysBetween(Calendar currentDCalendar, Calendar convertedCalendar) {
        long end = currentDCalendar.getTimeInMillis();
        long start = convertedCalendar.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }*/

    @Override
    public int getItemCount() {
        if(habits != null){
            return habits.size();
        }
        else return 0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

//    private void getCurCoins(String response) {
//        try{
//            JSONArray jArr = new JSONArray(response);
//            for(int i = 0; i < jArr.length(); i++){
//                JSONObject jObj = jArr.getJSONObject( i );
//                curCoins = jObj.getInt("Coins");
//            }
//        }
//        catch(JSONException e){
//            System.out.println(e);
//        }
//    }
//
//    private void getFirstDay(String response) {
//        try{
//            JSONArray jArr = new JSONArray(response);
//            for(int i = 0; i < jArr.length(); i++) {
//                JSONObject jObj = jArr.getJSONObject(i);
//                if (!jObj.getString("FirstDay").isEmpty()) {
//                    firstDayOfWeek = jObj.getString("FirstDay");
//                }
//            }
//        }
//        catch(JSONException e){
//            System.out.println(e);
//        }
//    }
}