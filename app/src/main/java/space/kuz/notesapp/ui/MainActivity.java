package space.kuz.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Pattern;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.domain.NotesRepository;
import space.kuz.notesapp.fragment.EditNoteFragment;
import space.kuz.notesapp.fragment.ListNoteFragment;
import space.kuz.notesapp.fragment.SettingFragment;
import space.kuz.notesapp.implementation.NotesRepositoryImplementation;

import static space.kuz.notesapp.CONSTANT.Constant.EDIT_NOTE;
import static space.kuz.notesapp.CONSTANT.Constant.positioN;

@SuppressLint("RestrictedApi")
public class MainActivity extends AppCompatActivity implements ListNoteFragment.Controller,
        EditNoteFragment.Controller {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();
    private NotesRepository notesRepository = new NotesRepositoryImplementation();
    private NotesAdapter adapter = new NotesAdapter();
    private EditText headEditText;
    private EditText descriptionEditText;
    private TextView dataTextView;
    private TextView dataYearTextView;
    private DatePicker datePicker;
    private String dataSave;

    private int position = 0;

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTestNotesRepository();

        if (savedInstanceState == null) {
            oneOpenFragment();
        }
        initBottomNavigationView();
        initNavigationView();


    }
    private void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(v -> {
            switch (v.getItemId()){
                case R.id.nav_setting:
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "defaLT", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private void initBottomNavigationView() {
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view) ;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_note:
                    Toast.makeText(this, "ЗАМЕТКИ", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_basket:
                    Toast.makeText(this, "КОРЗИНА", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_person:
                    Toast.makeText(this, "ЛИЧНЫЕ ДАННЫЕ", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "defaLT", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_note_item: {
                noteNull.setDate("");
                openNoteScreen(noteNull);
                positioN = -1;
                return true;
            }
            case R.id.save_note_item: {
                openScreenPostSave();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    @Override
    public void openListNote() {
        initRecyclerView();
    }

    @Override
    public void openEditNote() {
        initDataPicker();
        initEditText();
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

    private void oneOpenFragment() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_edit, EditNoteFragment.newInstance(notesRepository.getNotes().get(positioN)))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list, new ListNoteFragment())
                    .commit();
        }
    }

    private void openScreenPostSave() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_list, ListNoteFragment.newInstance(createNote()))
                .commit();
    }

    private Note createNote() {
        Note noteNew = new Note(headEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                dataSave);
        noteNew.setId(positioN + 1);
        return noteNew;
    }


    public void openNoteScreen(Note note) {
        twoOpenFragment(note);
    }

    private void twoOpenFragment(Note note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager().popBackStack();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_edit, EditNoteFragment.newInstance(note))
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_edit, EditNoteFragment.newInstance(note))
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }


    public void createNoteActivity(Note note) {
        notesRepository.createNote(new Note(note.getHead(), note.getDescription(), note.getDate()));

    }

    public void updateNoteActivity(Note note) {
        notesRepository.updateNote(note.getId(), note);

    }
    private void initEditText() {
        headEditText = findViewById(R.id.head_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        dataTextView = findViewById(R.id.data_text_view_create_note);
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setData(notesRepository.getNotes());
        adapter.setOnItemClickListener(this::onItemClick);
    }

    private void onItemClick(Note item) {
        openNoteScreen(item);
        positioN = item.getId() - 1;

    }


    private void initDataPicker() {
        datePicker = findViewById(R.id.data_picket);
        dataYearTextView = findViewById(R.id.data_text_year);

        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        dataSave = convertWriteData(day, month + 1, year);
        datePicker.init(year, month,
                day, (view, year1, monthOfYear, dayOfMonth) -> {
                    dataSave = convertWriteData(dayOfMonth, monthOfYear + 1, year1);
                });
    }

    private String convertWriteData(int day, int month, int year) {
        String s = Pattern.compile(R.string.data_text + "").toString();

        return convertWriteDayAndMonthData(day) + "." + convertWriteDayAndMonthData(month) + "." + year + (String) dataYearTextView.getText();
    }

    private String convertWriteDayAndMonthData(int day) {
        if (day < 10) {
            return "0" + day;
        } else {
            return "" + day;
        }
    }




}