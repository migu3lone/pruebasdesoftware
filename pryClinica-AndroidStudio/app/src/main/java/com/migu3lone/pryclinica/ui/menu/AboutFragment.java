package com.migu3lone.pryclinica.ui.menu;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.migu3lone.pryclinica.R;

public class AboutFragment extends DialogFragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //public View onCreateView(LayoutInflater inflater, ViewGroup container,
        //                         Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Configurar el Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.aboud), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar el VideoView
        VideoView videoView = view.findViewById(R.id.videoView);

        // Reemplaza con la IP privada de tu ordenador y la ruta del video
        String videoUrl = "http://192.168.20.22/pryClinica/resources/video.mp4";
        Uri uri = Uri.parse(videoUrl);

        // Configurar el MediaController para permitir la reproducción del video
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Establecer la URI del video

        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setOnCompletionListener(mediaPlayer -> videoView.start());

        // Inflar el layout del fragmento
        return view;
        //return inflater.inflate(R.layout.fragment_aboud, container, false);
    }
}