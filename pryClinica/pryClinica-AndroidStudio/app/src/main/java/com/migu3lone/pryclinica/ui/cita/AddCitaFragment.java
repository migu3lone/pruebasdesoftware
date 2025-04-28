package com.migu3lone.pryclinica.ui.cita;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.migu3lone.pryclinica.connection.ApiServiceCita;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;

public class AddCitaFragment extends Fragment {
    private EditText etIdCita, etFK_Paciente, etFK_Especialidad, etFK_Medico, etFecha, etHora;
    private Button btnGuardar;
    private ApiServiceCita apiCita;

    public AddCitaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addcita, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiCita = RetrofitClient.getClient().create(ApiServiceCita.class);

        etIdCita = view.findViewById(R.id.etIdCita);
        etFK_Paciente = view.findViewById(R.id.etFK_Paciente);
        etFK_Especialidad = view.findViewById(R.id.etFK_Especialidad);
        etFK_Medico = view.findViewById(R.id.etFK_Medico);
        etFecha = view.findViewById(R.id.etFecha);
        etFecha.setOnClickListener(v -> mostrarDatePickerDialog());
        etHora = view.findViewById(R.id.etHora);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarCita());
    }

    private void mostrarDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = year + "-" + (month + 1) + "-" + dayOfMonth;
                    etFecha.setText(fechaSeleccionada);
                },
                anio, mes, dia);
        datePickerDialog.show();
    }

    private void guardarCita() {
        String IdCita = etIdCita.getText().toString().trim();
        String FK_Paciente = etFK_Paciente.getText().toString().trim();
        String FK_Especialidad = etFK_Especialidad.getText().toString().trim();
        String FK_Medico = etFK_Medico.getText().toString().trim();
        String Fecha = etFecha.getText().toString().trim();
        String Hora = etHora.getText().toString().trim();

        if (FK_Paciente.isEmpty() || FK_Especialidad.isEmpty() || FK_Medico.isEmpty() || Fecha.isEmpty() || Hora.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject citaData = new JsonObject();
        citaData.addProperty("var1", IdCita);
        citaData.addProperty("var2", FK_Paciente);
        citaData.addProperty("var3", FK_Especialidad);
        citaData.addProperty("var4", FK_Medico);
        citaData.addProperty("var5", Fecha);
        citaData.addProperty("var6", Hora);

        Log.d("API_REQUEST", "Datos a enviar a la API: " + citaData.toString());

        apiCita.saveCita(citaData).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    String mensaje = IdCita.isEmpty() ? "Agregado con éxito" : "Actualizado con éxito";
                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                    Log.e("API_RESPONSE", "Valor de var1: " + FK_Paciente);
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
