package by.kos.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import by.kos.mynotes.databinding.ActivityMainBinding;
import by.kos.mynotes.model.Note;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotesAdapter adapter;

    public static final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if (notes.isEmpty()) {
            notes.add(new Note("Test1", "Descr1", "monday", 1));
            notes.add(new Note("Test2", "Descr2", "saturday", 2));
            notes.add(new Note("Test3", "Descr3", "friday", 3));
            notes.add(new Note("Test4", "Descr4", "saturday", 3));
            notes.add(new Note("Test5", "Descr5", "friday", 2));
            notes.add(new Note("Test6", "Descr6", "saturday", 1));
            notes.add(new Note("Test7", "Descr7", "monday", 3));
        }

        adapter = new NotesAdapter(notes);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
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
                Intent openAddNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(openAddNoteIntent);
            }
        });
    }

    private void deleteNote(int position){
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
}