package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.AppDatabase;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> mAllTodos;

    TodoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        todoDao = db.todoDao();
        mAllTodos = todoDao.getAllTodos();
    }

    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insert (Todo todo) {
        new insertAsyncTask(todoDao).execute(todo);
    }

    public void delete(Todo todo) {
        todoDao.delete(todo);
    }

    public void setRewardCoins(int newCoins, int idTodo) {
        todoDao.setRewardCoins(newCoins, idTodo);
    }

    private static class insertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao todoDao) {
            mAsyncTaskDao = todoDao;
        }

        @Override
        protected Void doInBackground(final Todo... todos) {
            mAsyncTaskDao.insert(todos[0]);
            return null;
        }
    }
}
