package tbc.dma.todo.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tbc.dma.todo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllTasksFragment extends Fragment {

    public AllTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        // Inflate the layout for this fragment
        return view;
    }
}
