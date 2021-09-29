package space.kuz.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView headTextView = itemView.findViewById(R.id.text_view_head);
    private TextView descriptionTextView = itemView.findViewById(R.id.text_view_description);
    private TextView dataTextView = itemView.findViewById(R.id.text_view_data);
    private Note note;

    public NoteViewHolder(@NonNull ViewGroup parent, NotesAdapter.onItemClickListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        itemView.setOnClickListener(v -> listener.onItemClick(note));
    }

    public void bind(Note note) {
        this.note = note;
        headTextView.setText(note.getHead());
        descriptionTextView.setText(note.getDescription());
        dataTextView.setText(note.getDate());
    }
}
