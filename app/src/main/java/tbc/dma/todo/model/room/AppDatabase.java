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

    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "TodoApp";

    private static final ExecutorService DB_NEW_FIXED_THREAD = Executors.newFixedThreadPool(3);
    public static ExecutorService getDbNewFixedThread() {
        Log.d("DBUGX", AppDatabase.class.getSimpleName() +"getDbNewFixedThread: Returning new THREAD");
        return DB_NEW_FIXED_THREAD;
    }


    public abstract TodoDao todoDao();
    private static AppDatabase appDatabaseInstance;

    public static AppDatabase getInstance(Context context){
        if(appDatabaseInstance == null){
            /*
              Synchronized method is a method which can be used by only one thread at a time.
              Other threads will be waiting until the method will be released.
              should have only serious reasons to declare method as synchronized
              because such method decreases the productivity.
              The classic case of synchronized method usage is when several
              threads are using same resources i.e. change state of some object
              and it is needed to make sure only one thread performs it at a time,
              otherwise it will cause inconsistency. Also make sure to make synchronized
              method as small as possible, ideally reduce it to contain only operations
              which can manipulate common resources.
              **/

            /*
              In this scenario Every DB operation happen one after another completion
              So running this operation in many thread wont be effective thats why
              just following the natural process of its algorithm

              */
            synchronized (LOCK){
                Log.d("DBUGX", AppDatabase.class.getSimpleName() +"Creating a new database instance; inside synchronized (getInstance(context)");
                appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d("DBUGX", "Exiting "+AppDatabase.class.getSimpleName() +". Getting the database instance: ");
        return appDatabaseInstance;
    }
}
