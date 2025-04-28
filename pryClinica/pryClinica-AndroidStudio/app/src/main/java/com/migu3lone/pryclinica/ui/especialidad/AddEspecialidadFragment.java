package com.migu3lone.pryclinica.ui.especialidad;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.migu3lone.pryclinica.R;
import com.migu3lone.pryclinica.connection.ApiServiceEspecialidad;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;

public class AddEspecialidadFragment extends Fragment {
    private EditText etIdEspecialidad, etEspecialidad;
    private Button btnGuardar;
    private ApiServiceEspecialidad apiEspecialidad;

    public AddEspecialidadFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addespecialidad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiEspecialidad = RetrofitClient.getClient().create(ApiServiceEspecialidad.class);

        // Inicializar vistas
        etIdEspecialidad = view.findViewById(R.id.etIdEspecialidad);
        etEspecialidad = view.findViewById(R.id.etEspecialidad);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        // Configurar el botón para enviar los datos
        btnGuardar.setOnClickListener(v -> guardarEspecialidad());
    }

    private void guardarEspecialidad() {
        // Obtener datos de los campos
        String IdEspecialidad = etIdEspecialidad.getText().toString().trim();
        String Especialidad = etEspecialidad.getText().toString().trim();

        // Validar campos obligatorios
        if (Especialidad.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el JsonObject con las variables
        JsonObject especialidadData = new JsonObject();
        especialidadData.addProperty("var1", IdEspecialidad);
        especialidadData.addProperty("var2", Especialidad);

        Log.d("API_REQUEST", "Datos a enviar a la API: " + especialidadData.toString());

        // Llamar a la API
        apiEspecialidad.saveEspecialidad(especialidadData).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    // Verifica si se debe agregar o actualizar la especialidad
                    String mensaje = IdEspecialidad.isEmpty() ? "Agregado con éxito" : "Actualizado con éxito";
                    //String mensaje = response.body().getMensaje();
                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                    Log.e("API_RESPONSE", "Valor de var1: " + Especialidad);
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("API_ERROR", "Error en la respuesta: " + errorResponse);
                        Toast.makeText(getContext(), "Error en la respuesta: " + errorResponse, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
