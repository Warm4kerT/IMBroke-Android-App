package pt.ubi.projindividual;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.UUID;

public class EditViewModel extends AndroidViewModel {

    private EventDao eventDao;
    private EventDataBase eventDB;

    public EditViewModel(@NonNull Application app){
        super(app);
        eventDB = EventDataBase.getDatabase(app);
        eventDao = eventDB.eventDao();
    }

    public LiveData<Event> getEvent(UUID eID){
        return eventDao.getEvent(eID);
    }
}
