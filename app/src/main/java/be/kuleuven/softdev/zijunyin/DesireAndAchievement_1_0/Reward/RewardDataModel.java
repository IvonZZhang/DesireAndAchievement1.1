package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

public class RewardDataModel extends DataModel{
    private String isRepeated;

    public RewardDataModel() {
    }

    public RewardDataModel(String txtRewardName, String isRepeated, String txtCoinNumber) {
        super();
        this.name = txtRewardName;
        this.isRepeated = isRepeated;
        //this.txtTimes = txtTimes;
        this.coins = txtCoinNumber;
    }

    String isRepeated() {
        return isRepeated;
    }

}
