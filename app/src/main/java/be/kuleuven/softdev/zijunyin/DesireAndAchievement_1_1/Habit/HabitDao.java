package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DateConverter;

@Dao
@TypeConverters(DateConverter.class)
public interface HabitDao {
//    @Query("SELECT * FROM Habit_table")
//    LiveData<List<Habit>> getAll();
//
//    @Query("SELECT * FROM Habit_table WHERE hid IN (:habitIds)")
//    LiveData<List<Habit>> loadAllByIds(int[] habitIds);
//
//    @Query("UPDATE Habit_table SET Timesdone = 0 WHERE hid = :hid")
//    void clearTimesDone(int hid);
//
////    @Query("UPDATE Habit_table SET CycleStartDate = :date WHERE hid = :id")
////    void setCycleStartDate(int hid);
//
    @Query("UPDATE habit_table SET Timesdone = Timesdone + 1 WHERE idHabit = :idHabit")
    void increaseTimesDone(int idHabit);
//
////    @Query("SELECT TimesDone FROM Habit_table WHERE hid = :hid")
//    LiveData<Integer> getTimesDone(int hid);
//
//    @Query("UPDATE Habit_table SET Timesdone = :timesdone WHERE hid = :hid")
//    void setTimesDone(int hid, int timesdone);

    @Insert
    void insert(Habit habit);

//    @Insert
//    void insertAll(Habit... habits);

    @Delete
    void delete(Habit habit);

    @Query("DELETE FROM habit_table")
    void deleteAll();

    @Query("SELECT * FROM habit_table")
    LiveData<List<Habit>> getAllHabits();

    @Query("UPDATE habit_table SET timesDone = :curTimesDone WHERE idHabit = :idHabit")
    void setTimesDone(int curTimesDone, int idHabit);

    @Query("SELECT cycleStartDate FROM habit_table WHERE idHabit = :idHabit")
    Date getCycleStartDate(int idHabit);

    @Query("UPDATE habit_table SET cycleStartDate = :curDate WHERE idHabit = :idHabit")
    void setCycleStartDate(Date curDate, int idHabit);
}
