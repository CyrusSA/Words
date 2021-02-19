package cyrussa.github.com.words;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.ViewModels.SearchActivityViewModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchItemAdapter searchItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        final SearchActivityViewModel viewModel = new ViewModelProvider(this).get(SearchActivityViewModel.class);
        viewModel.initialize();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchItemAdapter = new SearchItemAdapter();
        recyclerView.setAdapter(searchItemAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        viewModel.searchResults().observe(this, list -> searchItemAdapter.submitList(list));

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        if (!(query == null || query.isEmpty())) {
            viewModel.search(query);
        }
    }
}

class SearchItemAdapter extends ListAdapter<Song, SearchItemAdapter.ViewHolder> {

    public SearchItemAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Song> DIFF_CALLBACK = new DiffUtil.ItemCallback<Song>() {

        @Override
        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView artist;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title12);
            artist = itemView.findViewById(R.id.artist12);
        }

        public void bindTo(Song song) {
            this.artist.setText(song.getArtist());
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
