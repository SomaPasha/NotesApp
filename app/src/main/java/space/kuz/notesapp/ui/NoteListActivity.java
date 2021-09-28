package space.kuz.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Date;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.NoteStructure;
import space.kuz.notesapp.domain.NotesRepository;
import space.kuz.notesapp.implementation.NotesRepositoryImplementation;

public class NoteListActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private NotesRepository notesRepository = new NotesRepositoryImplementation();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        createTestNotesRepository();
        initToolBar();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_note_item: {
                openNoteScreen();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void openNoteScreen() {
        Intent intent = new Intent(this, NoteEditActivity.class);
        startActivity(intent);
    }

    private void createTestNotesRepository() {
        notesRepository.createNote(new NoteStructure("Заметка № 1", "Пойти в учить", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 2", "Пойти в школу", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 3", "Пойти на работу", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 4", "Пойти найти друга", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 5", "Пойти играть в баскетбол", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 6", "Пойти выучить английский", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 7", "Сходить в отпуск", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 8", "Найти себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 9", "Не переставать верить в себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 10", "Не переставать верить в других", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 11", "Найти себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 12", "Не переставать верить в себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 13", "Не переставать верить в других", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 14", "Найти себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 15", "Не переставать верить в себя", new Date()));
        notesRepository.createNote(new NoteStructure("Заметка № 16", "Не переставать верить в других", new Date()));

    }

    private void initToolBar() {
        toolbar = (MaterialToolbar) findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);

        DividerItemDecoration itemDecoration = new
                DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void onItemClick(NoteStructure item) {
        openNoteScreen();
    }

}