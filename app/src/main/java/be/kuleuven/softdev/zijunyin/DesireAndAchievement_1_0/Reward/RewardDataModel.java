package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Reward;

public class RewardDataModel {
    private String txtRewardName;
    private String isRepeated;
    private String txtCoinNumber;

    public RewardDataModel() {
    }

    RewardDataModel(String txtRewardName, String isRepeated, String txtCoinNumber) {
        this.txtRewardName = txtRewardName;
        this.isRepeated = isRepeated;
        //this.txtTimes = txtTimes;
        this.txtCoinNumber = txtCoinNumber;
    }

    String getTxtRewardName() {
        return txtRewardName;
    }

    String isRepeated() {
        return isRepeated;
    }

    String getTxtCoinNumber() {
        return txtCoinNumber;
    }
}
