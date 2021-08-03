package by.kos.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import by.kos.mynotes.databinding.NoteItemBinding;
import by.kos.mynotes.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> notes;
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    interface OnNoteClickListener {
        void onNoteClick(int position);
        void onNoteLongClick(int position);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.binding.tvTitle.setText(note.getTitle());
        holder.binding.tvDescription.setText(note.getDescription());
        holder.binding.tvDayOfWeek.setText(note.getDayOfWeek());
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.binding.getRoot().getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = holder.binding.getRoot().getResources().getColor(android.R.color.holo_orange_light);
                break;
            default:
                colorId = holder.binding.getRoot().getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.binding.tvTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        NoteItemBinding binding;

        public NotesViewHolder(@NonNull NoteItemBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            binding = itemViewBinding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onNoteClickListener != null){
                        onNoteClickListener.onNoteLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

}
