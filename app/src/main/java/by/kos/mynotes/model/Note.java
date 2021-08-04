package by.kos.mynotes.model;

import android.content.Context;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import by.kos.mynotes.R;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static String getDayAsString(Context context, int dayNumber) {
        String[] days = context.getResources().getStringArray(R.array.DaysOfWeek);
        String day = "";
        switch (dayNumber) {
            case 0:
                day = days[0];
                break;
            case 1:
                day = days[1];
                break;
            case 2:
                day = days[2];
                break;
            case 3:
                day = days[3];
                break;
            case 4:
                day = days[4];
                break;
            case 5:
                day = days[5];
                break;
            case 6:
                day = days[6];
                break;
        }
        return day;
    }
}
