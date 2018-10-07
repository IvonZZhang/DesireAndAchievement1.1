package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "reward_table")
public class Reward {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idReward")
    private int idReward;

    @NonNull
    @ColumnInfo(name = "rewardName")
    private String rewardName;

    @NonNull
    @ColumnInfo(name = "consumedCoins")
    private int consumedCoins;

    @NonNull
    @ColumnInfo(name = "isRepeatedIcon")
    private boolean isRepeated;

    public Reward(@NonNull String rewardName, @NonNull int consumedCoins, @NonNull boolean isRepeated) {
        this.rewardName = rewardName;
        this.consumedCoins = consumedCoins;
        this.isRepeated = isRepeated;
    }

    @NonNull
    public int getIdReward() {
        return idReward;
    }

    @NonNull
    public String getRewardName() {
        return rewardName;
    }

    @NonNull
    public int getConsumedCoins() {
        return consumedCoins;
    }

    @NonNull
    public boolean isRepeated() {
        return isRepeated;
    }

    public void setIdReward(@NonNull int idReward) {
        this.idReward = idReward;
    }
}
