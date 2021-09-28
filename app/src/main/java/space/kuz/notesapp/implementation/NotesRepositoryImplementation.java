package space.kuz.notesapp.implementation;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import space.kuz.notesapp.domain.NoteStructure;
import space.kuz.notesapp.domain.NotesRepository;

public class NotesRepositoryImplementation implements NotesRepository {
    private ArrayList<NoteStructure> notesArr = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<NoteStructure> getNotes() {
        return new ArrayList<>(notesArr); // TODO
    }

    @Nullable
    @Override
    public Integer createNote(NoteStructure note) {
        note.setId(++counter);
        notesArr.add(note);
        return (Integer) counter;
    }

    @Override
    public boolean deleteNote(Integer id) {
        for (int i = 0; i < notesArr.size(); i++) {
            if (id == notesArr.get(i).getId()) {
                notesArr.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateNote(Integer id, NoteStructure note) {
        deleteNote(id);
        note.setId(id);
        notesArr.add(id - 1, note);
        //note.setDate(new Date());
        return true;
    }


}
