package space.kuz.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.NoteStructure;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<NoteStructure> data = new ArrayList<>();
    private onItemClickListener listener=null;

    public void setData(List<NoteStructure> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteStructure note = getItem(position);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(note));
        holder.headTextView.setText(note.getHead());
        holder.descriptionTextView.setText(note.getDescription());
        holder.dataTextView.setText(note.getDate().toString());
    }

    private NoteStructure getItem(Integer position){
        return  data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener=listener;
    }

    public interface onItemClickListener{
        void onItemClick(NoteStructure item);
    }
}
