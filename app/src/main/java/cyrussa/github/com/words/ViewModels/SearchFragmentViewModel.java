package cyrussa.github.com.words.ViewModels;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cyrussa.github.com.words.Models.Song;
import cyrussa.github.com.words.Repositories.SearchRepository;

public class SearchFragmentViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Song>> searchResults = new MutableLiveData<>();
    private SearchRepository searchRepository;

    public void initialize() {
        this.searchRepository = new SearchRepository();
    }

    public void search(String query) {
        this.searchRepository.search(query, searchResults::setValue);
    }

    public LiveData<ArrayList<Song>> searchResults() {
        return this.searchResults;
    }
}
