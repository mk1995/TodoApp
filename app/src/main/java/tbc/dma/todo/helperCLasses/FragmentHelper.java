package tbc.dma.todo.helperCLasses;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tbc.dma.todo.adapter.RecyclerViewTaskListAdapter;

public class FragmentHelper extends Fragment {

    final ViewGroup mContainer;
    private final RecyclerView mRecyclerView;

    public FragmentHelper(RecyclerView recyclerView, ViewGroup container) {
        mRecyclerView = recyclerView;
        mContainer = container;
    }

    public RecyclerViewTaskListAdapter initRVHelper() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // 2. set layoutManger
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContainer.getContext(), LinearLayoutManager.VERTICAL, false ));

        // 3. create an adapter
        RecyclerViewTaskListAdapter mAdapter = new RecyclerViewTaskListAdapter(mContainer.getContext());

        // 4. set adapter
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(mContainer.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        return mAdapter;
    }
}
