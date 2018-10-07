package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoDao;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private LiveData<User> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getTheUser();
    }

    public LiveData<User> getTheUser() { return mAllUsers; }

    public void updateCoins(int newCoins){
        mRepository.updateCoins(newCoins);
    }

    public int getCoins(){
        return mRepository.getCoins();
    }

    public void insert(User user) { mRepository.insert(user); }
}
