package pt.ubi.projindividual;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Event.class, version = 1)
@TypeConverters({TypeConverter.class})
public abstract class EventDataBase extends RoomDatabase{

    public abstract EventDao eventDao();
    private static volatile EventDataBase roomInstance;

    static EventDataBase getDatabase(final Context context){
        if(roomInstance == null){
            synchronized (EventDataBase.class){
                if(roomInstance == null){
                    roomInstance = Room.databaseBuilder(context.getApplicationContext(),EventDataBase.class,"event_database").build();
                }
            }
        }

        return roomInstance;
    }
}


