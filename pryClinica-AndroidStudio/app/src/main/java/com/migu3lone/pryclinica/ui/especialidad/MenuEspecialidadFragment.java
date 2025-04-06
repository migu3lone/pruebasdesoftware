package com.migu3lone.pryclinica.ui.especialidad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.migu3lone.pryclinica.R;
import com.migu3lone.pryclinica.ui.adapter.MenuEspecialidadAdapter;

public class MenuEspecialidadFragment extends Fragment {

    public MenuEspecialidadFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menuespecialidad, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        MenuEspecialidadAdapter adapter = new MenuEspecialidadAdapter(getActivity());
        adapter.addFragment(new ViewEspecialidadFragment(), "View");
        adapter.addFragment(new AddEspecialidadFragment(), "Add");
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getTitle(position));
        }).attach();

        //return inflater.inflate(R.layout.fragment_menuespecialidad, container, false);
        return view;
    }
}
