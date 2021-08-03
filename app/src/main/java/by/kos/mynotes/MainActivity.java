package by.kos.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import by.kos.mynotes.databinding.ActivityMainBinding;
import by.kos.mynotes.model.Note;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

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

        NotesAdapter adapter = new NotesAdapter(notes);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvNotes.setAdapter(adapter);

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAddNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(openAddNoteIntent);
            }
        });
    }
}