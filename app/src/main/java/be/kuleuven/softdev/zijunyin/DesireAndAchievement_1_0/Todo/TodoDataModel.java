package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;

public class TodoDataModel {
    private String todoName;
    private String todoDDL;
    private String RewardCoins;
    //private boolean isDeleted;

    public TodoDataModel() {
    }

    public TodoDataModel(String todoName, String todoDDL, String rewardCoins) {
        this.todoName = todoName;
        this.todoDDL = todoDDL;
        RewardCoins = rewardCoins;
        //this.isDeleted = isDeleted;
    }

    public String getTodoName() {
        return todoName;
    }

    public String getTodoDDL() {
        return todoDDL;
    }

    public String getRewardCoins() {
        return RewardCoins;
    }

    //public boolean isDeleted() {
        //return isDeleted;
    //}
}
