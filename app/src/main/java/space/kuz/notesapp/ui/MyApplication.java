package space.kuz.notesapp.ui;

import android.app.Application;
import android.widget.Button;
import android.widget.Toast;

import space.kuz.notesapp.domain.Note;
import space.kuz.notesapp.domain.NotesRepository;
import space.kuz.notesapp.implementation.NotesRepositoryImplementation;

public class MyApplication extends Application {
    private NotesRepository notesRepository = new NotesRepositoryImplementation();

    public  NotesRepository getNotesRepository(){
        return notesRepository;
    }

    public void createTestNotesRepository() {
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
}
