package space.kuz.notesapp.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import space.kuz.notesapp.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {


    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public TextView headTextView = itemView.findViewById(R.id.text_view_head);
    public TextView descriptionTextView = itemView.findViewById(R.id.text_view_description);
    public TextView dataTextView    = itemView.findViewById(R.id.text_view_data);

}
