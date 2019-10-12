package cyrussa.github.com.words;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cyrussa.github.com.words.Models.Song;
import lombok.AllArgsConstructor;

public class SearchDisplayActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchItemAdapter searchItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);
        recyclerView = findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Song[] myDataset = { new Song("Hello", "Adele"), new Song("Bye", "Ya Boi"), new Song("Bydwade", "Ya Bodwi"), new Song("Bye", "Ya Boi")};

        // specify an adapter (see also next example)
        searchItemAdapter = new SearchItemAdapter(myDataset);
        recyclerView.setAdapter(searchItemAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @AllArgsConstructor
    public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder>{
        private Song[] songData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView title;
            public TextView artist;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title12);
                artist = itemView.findViewById(R.id.artist12);
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
            viewHolder.artist.setText(songData[position].getArtist());
            viewHolder.title.setText(songData[position].getTitle());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return songData.length;
        }

    }
}
