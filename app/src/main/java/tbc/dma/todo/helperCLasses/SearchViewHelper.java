package tbc.dma.todo.helperCLasses;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;

public class SearchViewHelper {

    public SearchViewHelper(Menu menu, MenuInflater inflater, final RecyclerViewTaskListAdapter mAdapter) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try{
                    mAdapter.getFilter().filter(query);
                }
                catch(Error e){
                    Log.d("Search", "Error: "+e.getMessage());
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try{
                    mAdapter.getFilter().filter(newText);
                }
                catch(Error e){
                    Log.d("Search", "Error: "+e.getMessage());
                }

                return false;
            }
        });
    }
}
