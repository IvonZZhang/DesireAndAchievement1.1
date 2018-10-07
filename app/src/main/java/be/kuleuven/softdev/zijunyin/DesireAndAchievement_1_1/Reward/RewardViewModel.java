package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class RewardViewModel extends AndroidViewModel {

    private RewardRepository mRepository;
    private LiveData<List<Reward>> mAllRewards;

    public RewardViewModel (Application application) {
        super(application);
        mRepository = new RewardRepository(application);
        mAllRewards = mRepository.getAllRewards();
    }

    public LiveData<List<Reward>> getAllRewards() { return mAllRewards; }

    public void insert(Reward reward) { mRepository.insert(reward); }

    public void delete(Reward reward) {
        mRepository.delete(reward);
    }
}