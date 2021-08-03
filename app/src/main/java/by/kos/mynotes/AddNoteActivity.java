package by.kos.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import by.kos.mynotes.databinding.ActivityAddNoteBinding;
import by.kos.mynotes.model.Note;

public class AddNoteActivity extends AppCompatActivity {
    private ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.etTitle.getText().toString();
                String description = binding.etDescription.getText().toString();
                String day = binding.spinnerDays.getSelectedItem().toString();
                int priority = 0;
                if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority1.getId()) {
                    priority = 1;
                } else if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority2.getId()) {
                    priority = 2;
                } else if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority3.getId()) {
                    priority = 3;
                }
                if(!title.isEmpty() && !description.isEmpty()) {
                    MainActivity.notes.add(new Note(title, description, day, priority));
                    Intent openMainIntent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(openMainIntent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty_fields_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}