package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.DataModel;

class RewardDataModel extends DataModel{
    private String isRepeated;

    RewardDataModel(int rewardId, String txtRewardName, String isRepeated, String txtCoinNumber) {
        super(rewardId, txtRewardName, txtCoinNumber);
        this.isRepeated = isRepeated;
    }

    String isRepeated() {
        return isRepeated;
    }

}
