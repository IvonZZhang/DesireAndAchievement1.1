package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class UserRepository {

    private UserDao userDao;
    private LiveData<User> mAllUsers;

    UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        mAllUsers = userDao.getTheUser();
    }

    LiveData<User> getTheUser() {
        return mAllUsers;
    }

    void updateCoins(int newCoins){
        userDao.updateCoins(newCoins);
    }

    public void insert(User user) {
        new insertAsyncTask(userDao).execute(user);
    }

    public int getCoins() {
        return userDao.getCoins();
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao userDao) {
            mAsyncTaskDao = userDao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            mAsyncTaskDao.insert(users[0]);
            return null;
        }
    }
}
