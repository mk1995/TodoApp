package tbc.dma.todo.model.room;

import android.util.Log;

import androidx.room.TypeConverter;

import java.sql.Date;


public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timeStamp){
        Log.d("DATE_CONVERTER", "toDate");
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        Log.d("DATE_CONVERTER", "toTimeStamp");
        return date == null ? null : date.getTime();
    }
}
