package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table WHERE idUser = 1")
    LiveData<User> getTheUser();

    /*@Query("SELECT * FROM user_table WHERE uid IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    LiveData<User> findByName(String first, String last);

    @Query("SELECT FirstDay FROM user_table")
    LiveData<String> getFirstDay();

    @Query("UPDATE user_table SET Coins = :newCoinNr WHERE uid = :uid")
    void setCoins(int newCoinNr, int uid);

    @Query("SELECT Coins FROM user_table WHERE uid = :uid")
    LiveData<Integer> getCoins(int uid);*/

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("UPDATE user_table SET coins = :newCoins WHERE idUser = 1")
    void updateCoins(int newCoins);

    @Query("SELECT coins FROM user_table WHERE idUser = 1")
    int getCoins();
}
