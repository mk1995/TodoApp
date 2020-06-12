package tbc.dma.todo.model.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tbc.dma.todo.model.dao.TodoDao;
import tbc.dma.todo.model.entity.TodoEntity;

@Database(version = 1, entities = {TodoEntity.class}, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static String DATABASE_NAME = "TodoApp";

    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(3);
    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }

    public abstract TodoDao todoDao();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                //Log.d(LOG_TAG, "Creating a new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        //Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }
}
