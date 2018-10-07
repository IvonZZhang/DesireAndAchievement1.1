package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RewardDao {
    @Query("SELECT * FROM reward_table")
    LiveData<List<Reward>> getAllRewards();

    @Insert
    void insert(Reward reward);

    @Insert
    void insert(Reward... rewards);

    @Delete
    void delete(Reward reward);

    @Query("DELETE FROM reward_table")
    void deleteAll();
}
