package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.DateConverter;

@Entity(tableName = "todo_table")
@TypeConverters(DateConverter.class)
public class Todo {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idTodo")
    private int idTodo;

    @ColumnInfo(name = "todoName")
    @NonNull
    private String todoName;

    @ColumnInfo(name = "deadline")
    @NonNull
    private Date deadline;// Data type might be changed later

    @ColumnInfo(name = "rewardCoins")
    @NonNull
    private int rewardCoins;

    public Todo(@NonNull String todoName, @NonNull Date deadline,
                @NonNull int rewardCoins) {
        this.todoName = todoName;
        this.deadline = deadline;
        this.rewardCoins = rewardCoins;
    }

    @NonNull
    public int getIdTodo() {
        return idTodo;
    }

    @NonNull
    public String getTodoName() {
        return todoName;
    }

    @NonNull
    public Date getDeadline() {
        return deadline;
    }

    @NonNull
    public int getRewardCoins() {
        return rewardCoins;
    }

    public void setIdTodo(@NonNull int idTodo) {
        this.idTodo = idTodo;
    }
}
