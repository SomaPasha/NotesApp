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
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.ui.fragment.BasketFragment;
import space.kuz.notesapp.ui.fragment.EditNoteFragment;
import space.kuz.notesapp.ui.fragment.ExitDialogFragment;
import space.kuz.notesapp.ui.fragment.ListNoteFragment;
import space.kuz.notesapp.ui.fragment.PersonFragment;
import space.kuz.notesapp.ui.fragment.SettingFragment;

import static space.kuz.notesapp.constant.Constant.positioN;

@SuppressLint("RestrictedApi")
public class MainActivity extends AppCompatActivity implements  ListNoteFragment.Controller,
        EditNoteFragment.Controller  {
    private BottomNavigationView bottomNavigationView;
    private Note noteNewPod ;
    private Map<Integer,Fragment> fragmentMap = new HashMap<>();

    private RecyclerView recyclerView;
    private Note noteNull = new Note();
    private Note noteNew = new Note();

    public NotesAdapter adapter = new NotesAdapter();
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
       getApp().createTestNotesRepository();
        if (savedInstanceState == null) {
            oneOpenFragment();
        }
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
                if(timeSave == null){ timeSave =convertWriteTime(Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE)) ;}
                noteNewPod = new Note(headEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        dataSave+" " +timeSave);
                noteNewPod.setId(positioN + 1);
                notifySubscribers(noteNewPod);
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
               setTimeTextView.setText(timeSave);
                if (dataSave == null){ dataSave = convertWriteData(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.YEAR));}
                noteNewPod = new Note(headEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        dataSave+" " +timeSave);
                noteNewPod.setId(positioN + 1);
                notifySubscribers(noteNewPod);
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

            getApp().getNotesRepository().deleteNote(noteNew.getId());
        adapter.setData( getApp().getNotesRepository().getNotes());
        } else if (item.getItemId()==R.id.redact_item_menu){
            onItemClick(noteNew);
            }
    }
public MyApplication getApp(){
        return  (MyApplication) getApplication();
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
            case R.id.button_exit: {
                exitApplication();
               // android.os.Process.killProcess(android.os.Process.myPid());
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

    private void exitApplication() {
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(getSupportFragmentManager(),null);
    }

    @Override
    public void openListNote() {
        initRecyclerView();
        if (positioN==-1) {
            scrollRecyclerView();
        }
    }
    private List<ListNoteFragment.Subscrite> subscriteList=new ArrayList<>();

    @Override
    public void subscribe(ListNoteFragment.Subscrite subscrite) {
        subscriteList.add(subscrite);
    }

    @Override
    public void unsubscribe(ListNoteFragment.Subscrite subscrite) {
        subscriteList.remove(subscrite);

    }

    @Override
    public void openEditNote() {
        initDataPicketDialog();
        initEditText();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
       // Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show();
     //   super.onCreate();
        super.onDestroy();
    }



    private void oneOpenFragment() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_edit, EditNoteFragment.newInstance(  getApp().getNotesRepository().getNotes().get(positioN)))
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
        dataSave=dataSave+timeSave;
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
       getApp().getNotesRepository().createNote(new Note(note.getHead(), note.getDescription(), note.getDate()));

    }

    public void updateNoteActivity(Note note) {
        getApp().getNotesRepository().updateNote(note.getId(), note);

    }

    private void initEditText() {
        headEditText = findViewById(R.id.head_edit_text);
        headEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

       @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeHead(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        descriptionEditText = findViewById(R.id.description_edit_text);
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeDescription(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dataTextView = findViewById(R.id.data_text_view_create_note);
    }

    private String savedataAndTime(){
        if (dataSave == null){ dataSave = convertWriteData(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+1,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.YEAR));}
        if(timeSave == null){ timeSave =convertWriteTime(Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE)) ;}
        return dataSave+" " +timeSave;
    }
    private void changeDescription(CharSequence s) {
        noteNewPod = new Note(headEditText.getText().toString(),
                s.toString(),
                savedataAndTime());
        noteNewPod.setId(positioN + 1);
        notifySubscribers(noteNewPod);
    }

    private void changeHead(CharSequence s) {
        noteNewPod = new Note(s.toString(),
                descriptionEditText.getText().toString(),
                savedataAndTime());
        noteNewPod.setId(positioN + 1);
        notifySubscribers(noteNewPod);

    }

    private void notifySubscribers(Note note) {
        for (ListNoteFragment.Subscrite subscrite : subscriteList) {
            subscrite.setNewMessage(note);
        }
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setData(getApp().getNotesRepository().getNotes());
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