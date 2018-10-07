package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class HabitViewModel extends AndroidViewModel {

    private HabitRepository mRepository;
    private LiveData<List<Habit>> mAllHabits;

    public HabitViewModel (Application application) {
        super(application);
        mRepository = new HabitRepository(application);
        mAllHabits = mRepository.getAllHabits();
    }

    public LiveData<List<Habit>> getAllHabits() { return mAllHabits; }

    public void insert(Habit habit) { mRepository.insert(habit); }

    public void delete(Habit habit){
        mRepository.delete(habit);
    }

    public void increaseTimesDone(Habit habit){
        mRepository.increaseTimesDone(habit);
    }

    public void setTimesDone(int curTimesDone, int idHabit) {
        mRepository.setTimesDone(curTimesDone, idHabit);
    }

    public Date getCycleStartDate(int idHabit){
        return mRepository.getCycleStartDate(idHabit);
    }

    public void setCycleStartDate(Date curDate, int idHabit){
        mRepository.setCycleStartDate(curDate, idHabit);
    }
}