package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class HabitDataModel extends DataModel {

    private String txtCycleAndTimes;

    public HabitDataModel() {
    }

    public HabitDataModel(String txtHabitName, String txtCycleAndTimes, String txtCoinNumber) {
        super();
        this.name = txtHabitName;
        this.txtCycleAndTimes = txtCycleAndTimes;
        //this.txtTimes = txtTimes;
        this.coins = txtCoinNumber;
    }

    String getTxtCycleAndTimes() {
        return txtCycleAndTimes;
    }
}
