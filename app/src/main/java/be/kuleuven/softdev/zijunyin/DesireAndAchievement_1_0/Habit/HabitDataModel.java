package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class HabitDataModel extends DataModel {

    private String txtCycleAndTimes;

    public HabitDataModel() {
    }

    public HabitDataModel(int habitId, String txtHabitName, String txtCycleAndTimes, String txtCoinNumber) {
        super(habitId, txtHabitName, txtCoinNumber);
        this.txtCycleAndTimes = txtCycleAndTimes;
    }

    String getTxtCycleAndTimes() {
        return txtCycleAndTimes;
    }
}
