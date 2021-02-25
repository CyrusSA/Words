package cyrussa.github.com.words;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cyrussa.github.com.words.ViewModels.SearchFragmentViewModel;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchItemAdapter searchItemAdapter;
    private SearchFragmentViewModel viewModel;

    private EditText songTitle;
    private Button searchButton;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songTitle = getView().findViewById(R.id.songTitle);
        searchButton = getView().findViewById(R.id.search_button);

        VolleyHelper.init(requireContext());
        viewModel = new ViewModelProvider(this).get(SearchFragmentViewModel.class);
        viewModel.initialize();

        setupRecyclerView();

        viewModel.searchResults().observe(this, searchItemAdapter::setList);

        searchButton.setOnClickListener(v -> viewModel.search(songTitle.getText().toString()));
    }

    private void setupRecyclerView() {
        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        searchItemAdapter = new SearchItemAdapter(song -> {
            Intent lyricsActivityIntent = new Intent(getContext(), DisplayLyricsActivity.class);
            lyricsActivityIntent.putExtra("song", song);
            startActivity(lyricsActivityIntent);
        });
        recyclerView.setAdapter(searchItemAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }
}