package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.GregorianCalendar;
import java.util.concurrent.Executors;

import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.Habit;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.Reward;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.Todo;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.User;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Habit.HabitDao;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Reward.RewardDao;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.Todo.TodoDao;
import be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1.User.UserDao;



@Database(entities = {Habit.class, Todo.class, Reward.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract HabitDao habitDao();
    public abstract TodoDao todoDao();
    public abstract RewardDao rewardDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "D&A_database")
//                            .addCallback(sAppDatabaseCallback)
//                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            getDatabase(context).userDao().insert(new User(1, 10, "Monday", "English"));
                                        }
                                    });
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .allowMainThreadQueries()
                            .build();
//                    INSTANCE.populateInitialData();
                }
            }
        }
        return INSTANCE;
    }

    private void populateInitialData() {
        if(userDao().getTheUser() == null){
            User user = new User(1, 10, "Monday", "Chinese");
            beginTransaction();
            try{
                userDao().insert(user);
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }

        }
    }

    private static RoomDatabase.Callback sAppDatabaseCallback = new RoomDatabase.Callback(){
        @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mUserDao;
        private final HabitDao mHabitDao;
        private final TodoDao mTodoDao;
        private final RewardDao mRewardDao;

        PopulateDbAsync(AppDatabase db) {
            mUserDao = db.userDao();
            mHabitDao = db.habitDao();
            mTodoDao = db.todoDao();
            mRewardDao = db.rewardDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();
//            User word = new User("Hello");
//            mDao.insert(word);
//            word = new User("World");
//            mDao.insert(word);
//            mHabitDao.deleteAll();
//            mRewardDao.deleteAll();
//            mTodoDao.deleteAll();
//            mUserDao.deleteAll();

            // TODO: 2018/9/16 测试一下这种初始化数据库的方式可不可以。可以的话还可以加最初的Habit之类的。

            //if(mUserDao.getTheUser() == null){
//                User user = new User(1, 10, "Monday", "Chinese");
//                mUserDao.insert(user);
//                Habit habit = new Habit("Habit Name", "Weekly", 3, 0, 10);
//                mHabitDao.insert(habit);
//                Todo todo = new Todo("Todo Name", GregorianCalendar.getInstance(), 15);
//                mTodoDao.insert(todo);
//                Reward reward = new Reward("Reward Name", 3, true);
//                mRewardDao.insert(reward);
            //}

//            mHabitDao.insert(new Habit("Habit name", "Weekly", 3, 0, 10));

            return null;
        }
    }

}

/*
AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "database-name").build();
 */




