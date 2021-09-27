package space.kuz.notesapp.domain;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface NotesRepository {
    List<NoteStructure> getNotes();

    @Nullable
    Integer createNote(NoteStructure note);

    boolean updateNote(Integer id, NoteStructure note);

    boolean deleteNote(Integer id);

}
