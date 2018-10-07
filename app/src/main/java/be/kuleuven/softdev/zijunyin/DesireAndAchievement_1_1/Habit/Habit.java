package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import javax.annotation.Nonnull;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DateConverter;

@Entity(tableName = "habit_table")
@TypeConverters(DateConverter.class)
public class Habit{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idHabit")
    private int idHabit;

    @ColumnInfo(name = "habitName")
    @NonNull
    private String habitName;

    @ColumnInfo(name = "habitCycle")
    @NonNull
    private String habitCycle;

    @ColumnInfo(name = "timePerCycle")
    @NonNull
    private int timesPerCycle;

    @ColumnInfo(name = "timesDone")
    @NonNull
    private int timesDone;

    @ColumnInfo(name = "rewardCoins")
    @NonNull
    private int rewardCoins;

    @ColumnInfo(name = "cycleStartDate")
    @Nonnull
    private Date cycleStartDate;

    // TODO: 2018/7/2 isDeleted to add undo function

    public Habit(@NonNull String habitName, @NonNull String habitCycle,
                 @NonNull int timesPerCycle, @NonNull int timesDone,
                 @NonNull int rewardCoins, @Nonnull Date cycleStartDate) {
        this.habitName = habitName;
        this.habitCycle = habitCycle;
        this.timesPerCycle = timesPerCycle;
        this.timesDone = timesDone;
        this.rewardCoins = rewardCoins;
        this.cycleStartDate = cycleStartDate;
    }

    @NonNull
    public int getIdHabit() {
        return idHabit;
    }

    @NonNull
    public String getHabitName() {
        return habitName;
    }

    @NonNull
    public String getHabitCycle() {
        return habitCycle;
    }

    @NonNull
    public int getTimesPerCycle() {
        return timesPerCycle;
    }

    @NonNull
    public int getTimesDone() {
        return timesDone;
    }

    @NonNull
    public int getRewardCoins() {
        return rewardCoins;
    }

    @Nonnull
    public Date getCycleStartDate() {
        return cycleStartDate;
    }

    public void setIdHabit(@NonNull int idHabit) {
        this.idHabit = idHabit;
    }
}
