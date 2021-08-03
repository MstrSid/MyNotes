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
    private SQLiteDatabase database;

    private final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new NotesDBHelper(this);

        database = dbHelper.getWritableDatabase();

        getData();

        adapter = new NotesAdapter(notes);
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
        int id = notes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        notes.clear();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotesContract.NotesEntry.COLUMN_PRIORITY);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int day_of_week = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(id, title, description, day_of_week, priority);
            notes.add(note);
        }
        cursor.close();
    }
}