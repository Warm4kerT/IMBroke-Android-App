package pt.ubi.projindividual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "Events")
public class Event implements Serializable {
    @PrimaryKey
    @NonNull
    private UUID id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private double amount;

    @NonNull
    private Date date;


    public Event(@NonNull UUID id, @NonNull String title, @Nullable String description, @NonNull double amount, @NonNull Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
    @NonNull
    public UUID getId() { return this.id; }

    @NonNull
    public String getTitle() {
        return this.title;
    }

    @NonNull
    public String getDescription() {
        return this.description;
    }

    @NonNull
    public double getAmount() {
        return this.amount;
    }

    @NonNull
    public Date getDate() { return this.date; }

    @Override
    public String toString() {
        return title + '\n' + description + '\n' + amount + "€ " + date.toString();
    }

    public static double totalSpent(List<Event> list){
        double res = 0;

        for(Event item : list){
            res += item.getAmount();
        }

        return res;
    }
}
