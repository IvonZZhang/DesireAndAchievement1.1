package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo_table")
    LiveData<List<Todo>> getAllTodos();

    @Insert
    void insert(Todo todo);

    @Insert
    void insertAll(Todo... todos);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("UPDATE todo_table SET rewardCoins = :newCoins WHERE idTodo = :idTodo")
    void setRewardCoins(int newCoins, int idTodo);
}
