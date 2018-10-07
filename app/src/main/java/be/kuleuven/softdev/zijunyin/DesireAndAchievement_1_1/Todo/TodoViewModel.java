package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;
    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel (Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos = mRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() { return mAllTodos; }

    public void insert(Todo todo) { mRepository.insert(todo); }

    public void delete(Todo todo) {
        mRepository.delete(todo);
    }

    public void setRewardCoins(int newCoins, int idTodo) {
        mRepository.setRewardCoins(newCoins, idTodo);
    }
}