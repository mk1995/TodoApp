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
                    if(query.length() != 0){
                        mAdapter.getFilter().filter(query);
                        //Log.d("Search", "Filter onQueryTextChange");
                    }else{
                        Log.d("DBUGX", "Query length is 0");
                    }
                }
                catch(Error e){
                    Log.d("DBUGX", "Error: "+e.getMessage());
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try{
                    if(newText.length() != 0){
                        mAdapter.getFilter().filter(newText);
                        //Log.d("Search", "Filter onQueryTextChange");
                    }else{
                        Log.d("DBUGX", "Query length is 0");
                    }
                }
                catch(Error e){
                    Log.d("DBUGX", "Error: "+e.getMessage());
                }

                return false;
            }
        });
    }
}
