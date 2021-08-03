package by.kos.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import by.kos.mynotes.contracts.NotesContract;
import by.kos.mynotes.databinding.ActivityAddNoteBinding;
import by.kos.mynotes.helpers.NotesDBHelper;
import by.kos.mynotes.model.Note;

public class AddNoteActivity extends AppCompatActivity {
    private ActivityAddNoteBinding binding;

    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();

        binding.btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.etTitle.getText().toString();
                String description = binding.etDescription.getText().toString();
                int day = binding.spinnerDays.getSelectedItemPosition();
                int priority = 0;
                if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority1.getId()) {
                    priority = 1;
                } else if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority2.getId()) {
                    priority = 2;
                } else if (binding.rgPriority.getCheckedRadioButtonId() == binding.rbPriority3.getId()) {
                    priority = 3;
                }
                if(!title.isEmpty() && !description.isEmpty()) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
                    contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
                    contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, day+1);
                    contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
                    database.insert(NotesContract.NotesEntry.TABLE_NAME, null ,contentValues);

                    Intent openMainIntent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(openMainIntent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty_fields_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}