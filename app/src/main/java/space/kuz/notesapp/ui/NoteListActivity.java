package space.kuz.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.domain.NotesRepository;
import space.kuz.notesapp.implementation.NotesRepositoryImplementation;
import space.kuz.notesapp.ui.fragment.EditNoteFragmentKotlin;

import static space.kuz.notesapp.constant.Constant.EDIT_NOTE;
//import static space.kuz.notesapp.CONSTANT.Constant.WRITE_TXT;

public class NoteListActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();
    private NotesRepository notesRepository = new NotesRepositoryImplementation();
    private NotesAdapter adapter = new NotesAdapter();
    private static final int REQUEST_CODE_NOTE_EDIT_ACTIVITY = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
       createTestNotesRepository();
    //    initToolBar();
      //  initRecyclerView();
       //createDecoration();
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
                noteNull.setDate("");
                openNoteScreen(noteNull);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    private void openNoteScreen(Note note) {
      //  Intent intent = new Intent(this, NoteEditActivity.class);
       // intent.putExtra(EDIT_NOTE, note);
     //   startActivityForResult(intent, REQUEST_CODE_NOTE_EDIT_ACTIVITY);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_list, new EditNoteFragmentKotlin())
                .addToBackStack(null)
                .commit();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_NOTE_EDIT_ACTIVITY) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {

            noteNew = data.getParcelableExtra(EDIT_NOTE);
            if (noteNew.getId() == null) {
                notesRepository.createNote(new Note(noteNew.getHead(), noteNew.getDescription(), noteNew.getDate()));
            } else {
                notesRepository.updateNote(noteNew.getId(), noteNew);
            }
            initRecyclerView();
        }

    }

    private void createTestNotesRepository() {
        notesRepository.createNote(new Note("Заметка № 1", "Пойти в учить", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 2", "Пойти в школу", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 3", "Пойти на работу", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 4", "Пойти найти друга", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 5", "Пойти играть в баскетбол", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 6", "Пойти выучить английский", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 7", "Сходить в отпуск", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 8", "Найти себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 9", "Не переставать верить в себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 10", "Не переставать верить в других", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 11", "Найти себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 12", "Не переставать верить в себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 13", "Не переставать верить в других", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 14", "Найти себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 15", "Не переставать верить в себя", "12.09.2012 года."));
        notesRepository.createNote(new Note("Заметка № 16", "Не переставать верить в других", "12.09.2012 года."));

    }

    private void initToolBar() {
        toolbar = (MaterialToolbar) findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(Note item) {
       // Integer i =  item.getId();
      openNoteScreen(item);
      //  writeFile();
       // readFile();
    }

    private void readFile() {
        File  myFile = new File("txt5.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader( new FileReader(myFile));
            String str="";
            Note noteWrite =new Note();
            int i=1;
           while ((str=br.readLine()) !=null){
               if(i==1){
                   Integer strInt = Integer.valueOf(str);
                   noteWrite.setId(strInt);
               } else if (i==2){
                   noteWrite.setHead(str);
               } else if (i==3){
                   noteWrite.setDescription(str);
               }else if (i==4){
                   noteWrite.setDate(str);
                   i=0;
               }
               if (i==0){
                 Integer j = noteWrite.getId();
                  notesRepository.updateNote(noteWrite.getId() ,noteWrite);
               }
               i++;
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFile() {
        File  myFile = new File("txt5.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter( new FileWriter(myFile));
        for (int i = 0; i <notesRepository.getNotes().size() ; i++) {
            bw.write(notesRepository.getNotes().get(i).getId()+"");
            bw.write("\n");
            bw.write(notesRepository.getNotes().get(i).getHead());
            bw.write("\n");
            bw.write(notesRepository.getNotes().get(i).getDescription());
            bw.write("\n");
            bw.write(notesRepository.getNotes().get(i).getDate());
            bw.write("\n");
        }
        bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDecoration() {
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
    }

}