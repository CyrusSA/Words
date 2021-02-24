package cyrussa.github.com.words;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Services.VolleyHelper;
import cyrussa.github.com.words.ViewModels.SearchActivityViewModel;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchItemAdapter searchItemAdapter;
    private SearchActivityViewModel viewModel;

    private EditText songTitle;
    private Button getLyrics;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songTitle = getView().findViewById(R.id.songTitle);
        getLyrics = getView().findViewById(R.id.search_button);

        VolleyHelper.init(requireContext());
        viewModel = new ViewModelProvider(this).get(SearchActivityViewModel.class);
        viewModel.initialize();

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        searchItemAdapter = new SearchItemAdapter();
        recyclerView.setAdapter(searchItemAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        viewModel.searchResults().observe(this, list -> searchItemAdapter.submitList(list));

        getLyrics.setOnClickListener(v -> viewModel.search(songTitle.getText().toString()));
    }
}

class SearchItemAdapter extends ListAdapter<Song, SearchItemAdapter.ViewHolder> {

    SearchItemAdapter() {
        super(DIFF_CALLBACK);
    }

    static final DiffUtil.ItemCallback<Song> DIFF_CALLBACK = new DiffUtil.ItemCallback<Song>() {

        @Override
        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.equals(newItem);
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView artist;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title12);
            artist = itemView.findViewById(R.id.artist12);
        }

        void bindTo(Song song) {
            this.artist.setText(String.join(", ", song.getArtists()));
            this.title.setText(song.getTitle());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_listitem, parent, false);
        ViewHolder vh = new ViewHolder(listItemView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindTo(getItem(position));
    }
}
