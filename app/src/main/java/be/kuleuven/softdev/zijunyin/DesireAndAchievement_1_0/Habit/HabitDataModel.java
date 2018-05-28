package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class HabitDataModel extends DataModel {

    private String txtCycleAndTimes;
    private String cycleStartDate;

    public HabitDataModel() {
    }

    public HabitDataModel(int habitId, String txtHabitName, String txtCycleAndTimes, String txtCoinNumber, String cycleStartDate) {
        super(habitId, txtHabitName, txtCoinNumber);
        this.txtCycleAndTimes = txtCycleAndTimes;
        this.cycleStartDate = cycleStartDate;
    }

    public String getCycleStartDate() {
        return cycleStartDate;
    }

    String getTxtCycleAndTimes() {
        return txtCycleAndTimes;
    }
}
