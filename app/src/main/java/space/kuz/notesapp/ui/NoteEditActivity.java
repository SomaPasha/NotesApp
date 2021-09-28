package space.kuz.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Date;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.NoteStructure;

import static space.kuz.notesapp.CONSTANT.Constant.EDIT_NOTE;

public class NoteEditActivity extends AppCompatActivity {
     private MaterialToolbar toolbar;
     private EditText headEditText;
     private EditText descriptionEditText;
     private NoteStructure noteStructure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        initToolBar();
        initEditText();
        noteStructure =  transferInEditText(noteStructure);
    }

    private NoteStructure transferInEditText(NoteStructure note) {
        note = getIntent().getExtras().getParcelable(EDIT_NOTE);
        headEditText.setText(note.getHead());
        descriptionEditText.setText(note.getDescription());
        return note;
    }

    private void initEditText() {
        headEditText= findViewById(R.id.head_edit_text);
        descriptionEditText =findViewById(R.id.description_edit_text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note_item: {
                Intent intentResult = new Intent();
                intentResult.putExtra(EDIT_NOTE, createNote());
                setResult(RESULT_OK,intentResult);
                finish();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private NoteStructure createNote() {
        NoteStructure  noteNew = new NoteStructure(headEditText.getText().toString(),
                                                descriptionEditText.getText().toString(),
                                                new Date());
         noteNew.setId(noteStructure.getId());
        return noteNew;
    }

    private void initToolBar() {
        toolbar = (MaterialToolbar) findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
    }


}