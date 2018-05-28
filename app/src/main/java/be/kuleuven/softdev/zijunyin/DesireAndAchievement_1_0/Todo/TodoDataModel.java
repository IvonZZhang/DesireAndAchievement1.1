package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class TodoDataModel extends DataModel {
    //private boolean isDeleted;
    private String todoDDL;

    public TodoDataModel() {
    }

    public TodoDataModel(int todoId, String todoName, String todoDDL, String rewardCoins) {
        super(todoId, todoName, rewardCoins);
        this.todoDDL = todoDDL;
    }

    //public boolean isDeleted() {
        //return isDeleted;
    //}

    public String getTodoDDL() {
        return todoDDL;
    }
}
