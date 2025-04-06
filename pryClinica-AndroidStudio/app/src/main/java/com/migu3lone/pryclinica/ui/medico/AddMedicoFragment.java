package com.migu3lone.pryclinica.ui.medico;

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
import com.migu3lone.pryclinica.connection.ApiServiceMedico;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;

public class AddMedicoFragment extends Fragment {
    private EditText etIdMedico, etPassword, etApellidos, etNombres, etDNI, etCMP, etDireccion, etCelular;
    private Button btnGuardar;
    private ApiServiceMedico apiMedico;

    public AddMedicoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addmedico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiMedico = RetrofitClient.getClient().create(ApiServiceMedico.class);

        etIdMedico = view.findViewById(R.id.etIdMedico);
        etPassword = view.findViewById(R.id.etPassword);
        etApellidos = view.findViewById(R.id.etApellidos);
        etNombres = view.findViewById(R.id.etNombres);
        etDNI = view.findViewById(R.id.etDNI);
        etCMP = view.findViewById(R.id.etCMP);
        etDireccion = view.findViewById(R.id.etDireccion);
        etCelular = view.findViewById(R.id.etCelular);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarMedico());
    }

    private void guardarMedico() {
        String IdMedico = etIdMedico.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
        String Apellidos = etApellidos.getText().toString().trim();
        String Nombres = etNombres.getText().toString().trim();
        String DNI = etDNI.getText().toString().trim();
        String CMP = etCMP.getText().toString().trim();
        String Direccion = etDireccion.getText().toString().trim();
        String Celular = etCelular.getText().toString().trim();

        if (Password.isEmpty() || Apellidos.isEmpty() || Nombres.isEmpty() || DNI.isEmpty() || CMP.isEmpty() || Direccion.isEmpty() || Celular.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject medicoData = new JsonObject();
        medicoData.addProperty("var1", IdMedico);
        medicoData.addProperty("var2", Password);
        medicoData.addProperty("var3", Apellidos);
        medicoData.addProperty("var4", Nombres);
        medicoData.addProperty("var5", DNI);
        medicoData.addProperty("var6", CMP);
        medicoData.addProperty("var7", Direccion);
        medicoData.addProperty("var8", Celular);

        Log.d("API_REQUEST", "Datos a enviar a la API: " + medicoData.toString());

        apiMedico.saveMedico(medicoData).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    String mensaje = IdMedico.isEmpty() ? "Agregado con éxito" : "Actualizado con éxito";
                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                    Log.e("API_RESPONSE", "Valor de var1: " + Password);
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
