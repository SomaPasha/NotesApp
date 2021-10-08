package space.kuz.notesapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.domain.NotesRepository;
import space.kuz.notesapp.fragment.BasketFragment;
import space.kuz.notesapp.fragment.EditNoteFragment;
import space.kuz.notesapp.fragment.ListNoteFragment;
import space.kuz.notesapp.fragment.PersonFragment;
import space.kuz.notesapp.fragment.SettingFragment;
import space.kuz.notesapp.implementation.NotesRepositoryImplementation;

import static space.kuz.notesapp.CONSTANT.Constant.positioN;

@SuppressLint("RestrictedApi")
public class MainActivity extends AppCompatActivity implements  ListNoteFragment.Controller,
        EditNoteFragment.Controller  {
    private BottomNavigationView bottomNavigationView;


    private Map<Integer,Fragment> fragmentMap = new HashMap<>();

    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();
    private NotesRepository notesRepository = new NotesRepositoryImplementation();
    private NotesAdapter adapter = new NotesAdapter();
    private EditText headEditText;
    private EditText descriptionEditText;
    private String dataSave;
    private  String timeSave;
    TextView dataTextView;

    TextView setDataTextView;
    TextView setTimeTextView;
   Button buttonData;
   Button buttonTime;
    int mYear, mMonth, mDay, mHour,mMinute;

    private int position = 0;

    private AppBarConfiguration mAppBarConfiguration;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTestNotesRepository();
        if (savedInstanceState == null) {
            oneOpenFragment();
        }
      //  ItemTouchHelper.Callback callback = new DragAndSwipe(adapter);
      //  ItemTouchHelper touchHelper= new ItemTouchHelper(callback);
    //    touchHelper.attachToRecyclerView(recyclerView);

    }

    private void initDataPicketDialog() {
        setDataTextView = findViewById(R.id.text_view_set_data);
        setTimeTextView = findViewById(R.id.text_view_set_time);

        buttonData= findViewById(R.id.button_set_data);
        buttonTime = findViewById(R.id.button_set_time);
        initBottomNavigationView();
        initNavigationView();
        buttonData.setOnClickListener(v -> {
            callDatePicket();
        });
        buttonTime.setOnClickListener(v -> {
            callTimePicket();
        });
    }

    private void callDatePicket() {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dataSave = convertWriteData(dayOfMonth,month+1,year);
                setDataTextView.setText(dataSave);
            }
        } , mYear,mMonth,mDay);
    datePickerDialog.show();
    }

    private void callTimePicket() {
        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               timeSave = convertWriteTime(hourOfDay,minute);
                     //  hourOfDay+ ":"+minute;
               setTimeTextView.setText(timeSave);
            }
        },mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.single_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        onContextItemClick(item);
        return  true;
    }
    private void onContextItemClick(@NonNull MenuItem item){
        if(item.getItemId()==R.id.delete_item_menu){
        notesRepository.deleteNote(noteNew.getId());
        adapter.setData(notesRepository.getNotes());
        } else if (item.getItemId()==R.id.redact_item_menu){
            onItemClick(noteNew);
            }
    }

    private void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(v -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment;
            switch (v.getItemId()){
                case R.id.nav_setting:
                    getSupportFragmentManager().popBackStack();
                    fragment = new SettingFragment();
                    break;
                default:
                    getSupportFragmentManager().popBackStack();
                    fragment = new SettingFragment();
            }

            fragmentTransaction.replace(R.id.fragment_edit_2, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            return true;
        });
    }

    private void initBottomNavigationView() {
        fragmentMap.put(R.id.nav_note, new ListNoteFragment() );
        fragmentMap.put(R.id.nav_basket, new BasketFragment() );
        fragmentMap.put(R.id.nav_person, new PersonFragment() );
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav_view) ;
            bottomNavigationView.setOnItemSelectedListener(item -> {
                if(item.getItemId() == R.id.nav_note) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_list, new ListNoteFragment())
                            .commit();

                } else {
                    getSupportFragmentManager().popBackStack();
                       getSupportFragmentManager()
                                 .beginTransaction()
                                 .replace(R.id.fragment_edit_2, Objects.requireNonNull(fragmentMap.get(item.getItemId())))
                               .addToBackStack(null)
                                .commit();
                }

                return true;
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
        if (positioN==-1) {
            scrollRecyclerView();
        }
    }

    @Override
    public void openEditNote() {
        initDataPicketDialog();
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
        sumDataAndTime();
        Note noteNew = new Note(headEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                dataSave);
        nullDataTime();
        noteNew.setId(positioN + 1);
        return noteNew;
    }

    private void nullDataTime() {
        dataSave =null;
        timeSave = null;
    }

    private void sumDataAndTime() {
        if (dataSave == null){ dataSave = convertWriteData(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.YEAR));}
        if(timeSave == null){ timeSave =convertWriteTime(Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE)) ;}
        dataSave= dataSave+" "+timeSave;
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
        adapter.setOnItemLongClickListener(this::onItemLongClick);
    }
    public void scrollRecyclerView(){
        recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
    }

    private void onItemClick(Note item) {
        openNoteScreen(item);
        positioN = item.getId() - 1;

    }

    private void onItemLongClick(Note itemNote, View itemView) {
        // Для реализации через контекстное меню
          noteNew =itemNote;
          itemView.setOnCreateContextMenuListener(this);
          itemView.showContextMenu();


        // Реализация через попул меню
    /*    PopupMenu popupMenu  = new  PopupMenu(this, itemView);
        popupMenu.inflate(R.menu.single_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
           // item.set
            itemView.getX();
            itemView.getY();
            Toast.makeText(this, "hh", Toast.LENGTH_SHORT).show();
            return true;
        });
        popupMenu.show();*/
    }

    private String convertWriteTime(int mHour, int mMinute) {
        return convertWriteDayAndMonthData(mHour) + ":" + convertWriteDayAndMonthData(mMinute);
    }

    private String convertWriteData(int day, int month, int year) {
        return convertWriteDayAndMonthData(day) + "." + convertWriteDayAndMonthData(month) + "." + year;
    }

    private String convertWriteDayAndMonthData(int day) {
        if (day < 10) {
            return "0" + day;
        } else {
            return "" + day;
        }
    }


}