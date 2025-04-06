package com.migu3lone.pryclinica.ui.paciente;

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
import com.migu3lone.pryclinica.connection.ApiServicePaciente;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;

public class AddPacienteFragment extends Fragment {
    private EditText etIdPaciente, etPassword, etApellidos, etNombres, etDNI, etNacimiento, etCorreo, etDireccion, etCelular;
    private RadioGroup rgSexo;
    //private RadioButton rbMasculino, rbFemenino;
    private Button btnGuardar;
    private ApiServicePaciente apiPaciente;

    public AddPacienteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addpaciente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiPaciente = RetrofitClient.getClient().create(ApiServicePaciente.class);

        // Inicializar vistas
        etIdPaciente = view.findViewById(R.id.etIdPaciente);
        etPassword = view.findViewById(R.id.etPassword);
        etApellidos = view.findViewById(R.id.etApellidos);
        etNombres = view.findViewById(R.id.etNombres);
        etDNI = view.findViewById(R.id.etDNI);
        rgSexo = view.findViewById(R.id.rgSexo);
        //rbMasculino = view.findViewById(R.id.rbMasculino);
        //rbFemenino = view.findViewById(R.id.rbFemenino);
        etNacimiento = view.findViewById(R.id.etNacimiento);
        etNacimiento.setOnClickListener(v -> mostrarDatePickerDialog());
        etCorreo = view.findViewById(R.id.etCorreo);
        etDireccion = view.findViewById(R.id.etDireccion);
        etCelular = view.findViewById(R.id.etCelular);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        // Configurar el botón para enviar los datos
        btnGuardar.setOnClickListener(v -> guardarPaciente());
    }

    private void mostrarDatePickerDialog() {
        // Obtener la fecha actual como predeterminada
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Formatear la fecha seleccionada
                    String fechaSeleccionada = year + "-" + (month + 1) + "-" + dayOfMonth;
                    etNacimiento.setText(fechaSeleccionada);
                },
                anio, mes, dia);
        datePickerDialog.show();
    }

    private void guardarPaciente() {
        // Obtener datos de los campos
        String IdPaciente = etIdPaciente.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
        String Apellidos = etApellidos.getText().toString().trim();
        String Nombres = etNombres.getText().toString().trim();
        String DNI = etDNI.getText().toString().trim();
        String Nacimiento = etNacimiento.getText().toString().trim();
        String Correo = etCorreo.getText().toString().trim();
        String Direccion = etDireccion.getText().toString().trim();
        String Celular = etCelular.getText().toString().trim();

        // Validar campos obligatorios
        int SexoId = rgSexo.getCheckedRadioButtonId();
        if (Password.isEmpty() || Apellidos.isEmpty() || Nombres.isEmpty() || DNI.isEmpty() || SexoId == -1 || Nacimiento.isEmpty() || Correo.isEmpty() || Direccion.isEmpty() || Celular.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        String sexoTexto = ((RadioButton) getView().findViewById(rgSexo.getCheckedRadioButtonId())).getText().toString();
        String Sexo = sexoTexto.equals("Masculino") ? "M" : "F";

        // Crear el JsonObject con las variables
        JsonObject pacienteData = new JsonObject();
        pacienteData.addProperty("var1", IdPaciente);
        pacienteData.addProperty("var2", Password);
        pacienteData.addProperty("var3", Apellidos);
        pacienteData.addProperty("var4", Nombres);
        pacienteData.addProperty("var5", DNI);
        pacienteData.addProperty("var6", Sexo);
        pacienteData.addProperty("var7", Nacimiento);
        pacienteData.addProperty("var8", Correo);
        pacienteData.addProperty("var9", Direccion);
        pacienteData.addProperty("var10", Celular);

        Log.d("API_REQUEST", "Datos a enviar a la API: " + pacienteData.toString());

        // Llamar a la API
        apiPaciente.savePaciente(pacienteData).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    // Verifica si se debe agregar o actualizar el paciente
                    String mensaje = IdPaciente.isEmpty() ? "Agregado con éxito" : "Actualizado con éxito";
                    //String mensaje = response.body().getMensaje();
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
