package by.kos.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import by.kos.mynotes.database.NotesDatabase;
import by.kos.mynotes.databinding.ActivityMainBinding;
import by.kos.mynotes.model.Note;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotesAdapter adapter;
    private MainViewModel viewModel;


    private final ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
        Note note = adapter.getNotes().get(position);
        viewModel.deleteNote(note);
    }

    private void getData() {
        LiveData<List<Note>> notesFromDb = viewModel.getNotes();
        notesFromDb.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                adapter.setNotes(notesFromLiveData);
            }
        });

    }

}