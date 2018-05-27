package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Habit;

public class HabitDataModel {

    private String txtHabitName;
    private String txtCycleAndTimes;
    private String txtCoinNumber;

    public HabitDataModel() {
    }

    HabitDataModel(String txtHabitName, String txtCycleAndTimes, String txtCoinNumber) {
        this.txtHabitName = txtHabitName;
        this.txtCycleAndTimes = txtCycleAndTimes;
        //this.txtTimes = txtTimes;
        this.txtCoinNumber = txtCoinNumber;
    }

    String getTxtHabitName() {
        return txtHabitName;
    }

    String getTxtCycleAndTimes() {
        return txtCycleAndTimes;
    }

    String getTxtCoinNumber() {
        return txtCoinNumber;
    }
}
