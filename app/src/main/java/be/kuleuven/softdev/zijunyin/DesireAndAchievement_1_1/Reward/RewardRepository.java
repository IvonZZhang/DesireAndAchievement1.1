package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class RewardRepository {

    private RewardDao rewardDao;
    private LiveData<List<Reward>> mAllReward;

    public RewardRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        rewardDao = db.rewardDao();
        mAllReward = rewardDao.getAllRewards();
    }

    LiveData<List<Reward>> getAllRewards(){
        return mAllReward;
    }

    public void insert(Reward reward){
        new insertAsyncTask(rewardDao).execute(reward);
    }

    public void delete(Reward reward) {
        rewardDao.delete(reward);
    }

    private static class insertAsyncTask extends AsyncTask<Reward, Void, Void> {

        private RewardDao mAsyncTaskDao;

        public insertAsyncTask(RewardDao rewardDao) {
            mAsyncTaskDao = rewardDao;
        }

        @Override
        protected Void doInBackground(Reward... rewards) {
            mAsyncTaskDao.insert(rewards[0]);
            return null;
        }
    }
}
