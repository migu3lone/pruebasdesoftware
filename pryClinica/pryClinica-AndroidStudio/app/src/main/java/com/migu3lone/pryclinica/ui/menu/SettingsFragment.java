package com.migu3lone.pryclinica.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import com.migu3lone.pryclinica.R;

public class SettingsFragment extends DialogFragment {

//public class SettingsFragment extends PreferenceFragmentCompat {

    /*@Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento de preferencias
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Configurar el fragmento de preferencias
        if (getChildFragmentManager().findFragmentById(R.id.settings_container) == null) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.settings_container, new SettingsPreferenceFragment())
                    .commit();
        }

        return view;
    }

    public static class SettingsPreferenceFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

}