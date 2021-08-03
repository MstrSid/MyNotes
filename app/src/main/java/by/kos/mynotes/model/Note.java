package by.kos.mynotes.model;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import by.kos.mynotes.MainActivity;
import by.kos.mynotes.R;

public class Note {
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

    public static String getDayAsString(Context context, int dayNumber) {
        String[] days = context.getResources().getStringArray(R.array.DaysOfWeek);
        String day = "";
        switch (dayNumber) {
            case 1:
                day = days[0];
                break;
            case 2:
                day = days[1];
                break;
            case 3:
                day = days[2];
                break;
            case 4:
                day = days[3];
                break;
            case 5:
                day = days[4];
                break;
            case 6:
                day = days[5];
                break;
            case 7:
                day = days[6];
                break;
        }
        return day;
    }
}
