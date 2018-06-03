package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

class TodoDataModel extends DataModel {
    private String todoDDL;

    TodoDataModel(int todoId, String todoName, String todoDDL, String rewardCoins) {
        super(todoId, todoName, rewardCoins);
        this.todoDDL = todoDDL;
    }

    String getTodoDDL() {
        return todoDDL;
    }
}
