package com.migu3lone.pryclinica.ui.paciente;

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
import com.migu3lone.pryclinica.ui.adapter.MenuPacienteAdapter;

public class MenuPacienteFragment extends Fragment {

    public MenuPacienteFragment() {

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menupaciente, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        MenuPacienteAdapter adapter = new MenuPacienteAdapter(getActivity());
        adapter.addFragment(new ViewPacienteFragment(), "View");
        adapter.addFragment(new AddPacienteFragment(), "Add");
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getTitle(position));
        }).attach();

        return view;
    }
}
