package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class HabitRepository {

    private HabitDao habitDao;
    private LiveData<List<Habit>> mAllHabits;

    public HabitRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.habitDao = db.habitDao();
        this.mAllHabits = db.habitDao().getAllHabits();
    }

    public LiveData<List<Habit>> getAllHabits(){
        return mAllHabits;
    }

    public void insert(Habit habit){
        new insertAsyncTask(habitDao).execute(habit);
    }

    public void delete(Habit habit){
        habitDao.delete(habit);
    }

    public void increaseTimesDone(Habit habit){
        habitDao.increaseTimesDone(habit.getIdHabit());
    }

    public void setTimesDone(int curTimesDone, int idHabit) {
        habitDao.setTimesDone(curTimesDone, idHabit);
    }

    public Date getCycleStartDate(int idHabit) {
        return habitDao.getCycleStartDate(idHabit);
    }

    public void setCycleStartDate(Date curDate, int idHabit) {
        habitDao.setCycleStartDate(curDate, idHabit);
    }

    private static class insertAsyncTask extends AsyncTask<Habit, Void, Void>{

        private  HabitDao mAsyncTaskDao;

        public insertAsyncTask(HabitDao habitDao) {
            mAsyncTaskDao = habitDao;
        }

        @Override
        protected Void doInBackground(Habit... habits) {
            mAsyncTaskDao.insert(habits[0]);
            return null;
        }
    }
}
