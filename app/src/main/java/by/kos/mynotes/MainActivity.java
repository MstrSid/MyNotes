package by.kos.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import by.kos.mynotes.contracts.NotesContract;
import by.kos.mynotes.databinding.ActivityMainBinding;
import by.kos.mynotes.helpers.NotesDBHelper;
import by.kos.mynotes.model.Note;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;

    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new NotesDBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /*if (notes.isEmpty()) {
            notes.add(new Note("Test1", "Descr1", "monday", 1));
            notes.add(new Note("Test2", "Descr2", "saturday", 2));
            notes.add(new Note("Test3", "Descr3", "friday", 3));
            notes.add(new Note("Test4", "Descr4", "saturday", 3));
            notes.add(new Note("Test5", "Descr5", "friday", 2));
            notes.add(new Note("Test6", "Descr6", "saturday", 1));
            notes.add(new Note("Test7", "Descr7", "monday", 3));
        }

        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }*/

        ArrayList<Note> notesFromDB = new ArrayList<>();

        @SuppressLint("Recycle")
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String day_of_week = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(title, description, day_of_week, priority);
            notesFromDB.add(note);
        }
        cursor.close();

        adapter = new NotesAdapter(notesFromDB);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rvNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
            }

            @Override
            public void onNoteLongClick(int position) {
                deleteNote(position);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        deleteNote(viewHolder.getAdapterPosition());
                    }
                });

        itemTouchHelper.attachToRecyclerView(binding.rvNotes);

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddNoteIntent = new Intent(MainActivity.this,
                        AddNoteActivity.class);
                startActivity(openAddNoteIntent);
            }
        });
    }

    private void deleteNote(int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
}