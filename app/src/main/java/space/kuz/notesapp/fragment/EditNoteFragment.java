package space.kuz.notesapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.ui.MainActivity;

public class EditNoteFragment  extends Fragment {
 private EditText headEditText;
    private EditText descriptionEditText;
    private TextView dataTextView;
    Note note;
    MaterialToolbar toolbar;
    private EditNoteFragment.Controller controller;
    static final String ARG_NOTE = "NOTE";
   //private static int position = 0;
    //private static final   String CURRENT_NOTE= "CURRENT_NOTE";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ListNoteFragment.Controller){
            controller = (EditNoteFragment.Controller)context;
        } else{
            throw  new IllegalStateException("Activity must implement EditNoteFragment.Controller");
        }
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
      setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putInt(CURRENT_NOTE, position);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_note_edit,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headEditText = view.findViewById(R.id.head_edit_text);
        descriptionEditText = view.findViewById(R.id.description_edit_text);
        dataTextView = view.findViewById(R.id.data_text_view_create_note);
        Bundle bundle = getArguments();
        if(bundle!=null){
            note = bundle.getParcelable(ARG_NOTE);
            headEditText.setText(note.getHead());
            descriptionEditText.setText(note.getDescription());
            dataTextView.setText(dataTextView.getText()+note.getDate());
               }
        toolbar = (MaterialToolbar) view.findViewById(R.id.note_edit_toolbar);
        ((MainActivity)requireActivity()).setSupportActionBar(toolbar);
        controller.openEditNote();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private EditNoteFragment.Controller getController(){
        return (EditNoteFragment.Controller) requireActivity();
    }


    public static EditNoteFragment newInstance(Note note){
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle  bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE,note);
        fragment.setArguments(bundle);
      //  position = note.getId();
        return fragment;
    }

    public interface Controller {
        void openEditNote();
    }

    @Override
    public void onDestroy() {
        controller = null;
        super.onDestroy();
    }
}
