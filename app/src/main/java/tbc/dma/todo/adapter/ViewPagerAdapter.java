package tbc.dma.todo.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragments = new ArrayList<>();
    private final List<String> lstTitles = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFragments.get(position);
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    public void AddFragment(Fragment fm, String title){
        lstFragments.add(fm);
        lstTitles.add(title);
    }
}
