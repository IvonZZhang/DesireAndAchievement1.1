package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class HabitDataModel extends DataModel {

    private String habitCycle;
    private String timesDone;
    private String timesPerCycle;
    private String cycleStartDate;

    public HabitDataModel(int habitId, String txtHabitName, String habitCycle, String timesDone, String timesPerCycle, String txtCoinNumber, String cycleStartDate) {
        super(habitId, txtHabitName, txtCoinNumber);
        this.habitCycle = habitCycle;
        this.timesDone = timesDone;
        this.timesPerCycle = timesPerCycle;
        this.cycleStartDate = cycleStartDate;
    }

    public String getCycleStartDate() {
        return cycleStartDate;
    }

    public String getHabitCycle() {
        return habitCycle;
    }

    public String getTimesDone() {
        return timesDone;
    }

    public String getTimesPerCycle() {
        return timesPerCycle;
    }

}
