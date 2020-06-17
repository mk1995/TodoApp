package tbc.dma.todo.helperCLasses;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import tbc.dma.todo.R;
import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;

public class SearchViewHelper {
    private final Context mContext;

    public SearchViewHelper(Menu menu, MenuInflater inflater, final RecyclerViewTaskListAdapter mAdapter, Context context) {
        inflater.inflate(R.menu.menu_search, menu);
        mContext = context;
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            Toast toast;
            @Override
            public boolean onQueryTextSubmit(String query) {
                try{
                    mAdapter.getFilter().filter(query);
                }
                catch(Exception e){
                    toast = Toast.makeText(mContext, "Submit: "+e.getMessage(), Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try{
                    mAdapter.getFilter().filter(newText);
                }
                catch(Exception e){
                    toast = Toast.makeText(mContext, "ExceptionHandling: "+e.getMessage(), Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                return false;
            }
        });
    }
}
