package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

class HabitDataModel extends DataModel {

    private String habitCycle;
    private String timesDone;
    private String timesPerCycle;
    private String cycleStartDate;

    HabitDataModel(int habitId, String txtHabitName, String habitCycle, String timesDone, String timesPerCycle, String txtCoinNumber, String cycleStartDate) {
        super(habitId, txtHabitName, txtCoinNumber);
        this.habitCycle = habitCycle;
        this.timesDone = timesDone;
        this.timesPerCycle = timesPerCycle;
        this.cycleStartDate = cycleStartDate;
    }

    String getCycleStartDate() {
        return cycleStartDate;
    }

    String getHabitCycle() {
        return habitCycle;
    }

    String getTimesDone() {
        return timesDone;
    }

    String getTimesPerCycle() {
        return timesPerCycle;
    }

    void setTimesDone(String newTimes){timesDone = newTimes;}

}
