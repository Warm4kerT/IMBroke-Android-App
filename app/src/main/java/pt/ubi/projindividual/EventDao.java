package pt.ubi.projindividual;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Dao
public interface EventDao {

    @Insert
    void insert(Event e);

    @Query("SELECT * FROM Events")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM Events WHERE id=:eID")
    LiveData<Event> getEvent(UUID eID);

    @Update
    void update(Event e);

    @Delete
    int delete(Event e);

    @Query("SELECT * FROM Events WHERE date BETWEEN :from AND :to")
    LiveData<List<Event>> findEventBetween(Date from, Date to);

}
