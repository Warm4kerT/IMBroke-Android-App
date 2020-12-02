package pt.ubi.projindividual;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventViewModel extends AndroidViewModel{
    private final EventDao eventDao;
    private final EventDataBase eventDB;
    private LiveData<List<Event>> allEvents;
    private Calendar today = Calendar.getInstance();

    public EventViewModel(Application app){
        super(app);

        eventDB = EventDataBase.getDatabase(app);
        eventDao = eventDB.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public void insert(Event e){ new InsertAsyncTask(eventDao).execute(e); }

    public void delete(Event e) { new DeleteAsyncTask(eventDao).execute(e); }

    public void update(Event e) { new UpdateAsyncTask(eventDao).execute(e); }

    LiveData<List<Event>> findEventBetween(Date from, Date to){ return eventDao.findEventBetween(from, to); }

    LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    LiveData<List<Event>> findEventMonth() {
        Calendar fDay = Calendar.getInstance();
        fDay.set(Calendar.DATE,today.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar lDay = Calendar.getInstance();
        lDay.set(Calendar.DATE,today.getActualMaximum(Calendar.DAY_OF_MONTH));

        return eventDao.findEventBetween(fDay.getTime(),lDay.getTime());
    }

    private class OperationsAsyncTask extends AsyncTask<Event, Void, Void> {

        EventDao mAsyncTaskDao;

        OperationsAsyncTask(EventDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Event, Void, Void> {

        EventDao eDao;

        public InsertAsyncTask(EventDao e) {
            this.eDao = e;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eDao.insert(events[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(EventDao eventDao) { super(eventDao); }

        @Override
        protected Void doInBackground(Event... events) {
            mAsyncTaskDao.update(events[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(EventDao eventDao) {
            super(eventDao);
        }

        @Override
        protected Void doInBackground(Event... events) {
            mAsyncTaskDao.delete(events[0]);
            return null;
        }
    }
}
