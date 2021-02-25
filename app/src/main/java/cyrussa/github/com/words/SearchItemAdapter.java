package cyrussa.github.com.words;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cyrussa.github.com.words.Models.Song;

class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    public interface  OnItemClickListener {
        void onItemClick(Song song);
    }

    private List<Song> searchResults;
    private OnItemClickListener clickListener;

    SearchItemAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.searchResults = new ArrayList<>();
    }

    void setList(List<Song> list){
        this.searchResults = list;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_listitem, parent, false);
        ViewHolder vh = new ViewHolder(listItemView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindTo(searchResults.get(position), clickListener);
    }

    @Override
    public int getItemCount(){
        return this.searchResults.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView artist;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
        }

        void bindTo(Song song, OnItemClickListener clickListener) {
            this.artist.setText(String.join(", ", song.getArtists()));
            this.title.setText(song.getTitle());
            itemView.setOnClickListener(v -> clickListener.onItemClick(song));
        }
    }
}
