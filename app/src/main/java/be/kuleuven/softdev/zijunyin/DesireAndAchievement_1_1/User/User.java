package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "idUser")
    private int idUser;

    @ColumnInfo(name = "coins")
    @NonNull
    public int coins;

    @ColumnInfo(name = "firstDay")
    @NonNull
    public String firstDay;

    @ColumnInfo(name = "language")
    @NonNull
    public String language;

    public User(@NonNull int idUser, @NonNull int coins, @NonNull String firstDay, @NonNull String language) {
        this.idUser = idUser;
        this.coins = coins;
        this.firstDay = firstDay;
        this.language = language;
    }

    @NonNull
    public int getIdUser() {
        return idUser;
    }

    @NonNull
    public int getCoins() {
        return coins;
    }

    @NonNull
    public String getFirstDay() {
        return firstDay;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }
}
