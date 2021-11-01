package space.kuz.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import space.kuz.notesapp.R;
import space.kuz.notesapp.domain.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> /* implements ItemTouchHelperAdapter*/ {
    private List<Note> data = new ArrayList<>();
    private onItemClickListener listener=null;
    private NotesAdapter.onItemLongClickListener listenerLong= null;


    public void setData(List<Note> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent,listener, listenerLong);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
    public Note getItem(Integer position){
        return  data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener=listener;
    }
    public void setOnItemLongClickListener(onItemLongClickListener listenerLong) {
        this.listenerLong=listenerLong;
    }

    public interface onItemClickListener{
        void onItemClick(Note item);
    }

    public  interface  onItemLongClickListener{
        void onItemLongClick(Note item, View itemView);
    }
 /*   @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for (int i = fromPosition; i <toPosition ; i++) {
                Collections.swap(data,i,i+1);
            }
        }
        else {
            for (int i = fromPosition; i >toPosition ; i--) {
                Collections.swap(data,i,i-1);
            }
        }
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
    data.remove(position);
     notifyItemRemoved(position);
    } */


}
