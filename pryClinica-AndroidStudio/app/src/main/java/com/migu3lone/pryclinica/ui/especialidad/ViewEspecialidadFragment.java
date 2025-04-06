package com.migu3lone.pryclinica.ui.especialidad;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
//import java.util.ArrayList;
//import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.JsonObject;
import com.migu3lone.pryclinica.R;
import com.migu3lone.pryclinica.beans.especialidad;
import com.migu3lone.pryclinica.beans.medico;
import com.migu3lone.pryclinica.beans.cita;
import com.migu3lone.pryclinica.beans.paciente;
import com.migu3lone.pryclinica.connection.ApiServiceEspecialidad;
import com.migu3lone.pryclinica.connection.ApiServiceMedico;
import com.migu3lone.pryclinica.connection.ApiServiceCita;
import com.migu3lone.pryclinica.connection.ApiServicePaciente;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;
import com.migu3lone.pryclinica.ui.adapter.ViewEspecialidadAdapter;
import com.migu3lone.pryclinica.ui.adapter.ViewMedicoAdapter;
import com.migu3lone.pryclinica.ui.adapter.ViewCitaAdapter;
import com.migu3lone.pryclinica.ui.adapter.ViewPacienteAdapter;

import java.io.IOException;

public class ViewEspecialidadFragment extends Fragment  {

    private SearchView searchView;
    private RecyclerView recyclerEspecialidad;
    private RecyclerView recyclerMedico;
    private RecyclerView recyclerCita;
    private RecyclerView recyclerPaciente;
    private ViewEspecialidadAdapter adapterEspecialidad;
    private ViewMedicoAdapter adapterMedico;
    private ViewCitaAdapter adapterCita;
    private ViewPacienteAdapter adapterPaciente;
    private ApiServiceEspecialidad apiEspecialidad;
    private ApiServiceMedico apiMedico;
    private ApiServiceCita apiCita;
    private ApiServicePaciente apiPaciente;
    private View modalAddView;
    private AlertDialog dialog;


    public ViewEspecialidadFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Infla el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_viewespecialidad, container, false);


        Button btnShowModal = view.findViewById(R.id.btnShowModal);
        btnShowModal.setOnClickListener(v -> showFormModal("add"));

        View itemView = inflater.inflate(R.layout.item_especialidad, container, false);
        ImageView editarButton = itemView.findViewById(R.id.editar);
        editarButton.setOnClickListener(v -> showFormModal("edit"));

        // Configura SearchView
        searchView = view.findViewById(R.id.searchView);
        setupSearchView();

        // Configura RecyclerView de Especialidad
        recyclerEspecialidad = view.findViewById(R.id.recyclerEspecialidad);
        recyclerEspecialidad.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura RecyclerView de Medico
        recyclerMedico = view.findViewById(R.id.recyclerMedico);
        recyclerMedico.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura RecyclerView de Cita
        recyclerCita = view.findViewById(R.id.recyclerCita);
        recyclerCita.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura RecyclerView de Paciente
        recyclerPaciente = view.findViewById(R.id.recyclerPaciente);
        recyclerPaciente.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener instancia de Retrofit y del servicio API
        apiEspecialidad = RetrofitClient.getClient().create(ApiServiceEspecialidad.class);
        apiMedico = RetrofitClient.getClient().create(ApiServiceMedico.class);
        apiCita = RetrofitClient.getClient().create(ApiServiceCita.class);
        apiPaciente = RetrofitClient.getClient().create(ApiServicePaciente.class);

        // Carga todas los items en el RecyclerView
        loadAll();
        return view;
    }


    private void showFormModal(String valor) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Si modalAddView ya tiene un padre, lo eliminamos
        if (modalAddView != null && modalAddView.getParent() != null) {
            ((ViewGroup) modalAddView.getParent()).removeView(modalAddView);
        }

        // Inflar el diseño del formulario si es nulo
        if (modalAddView == null) {
            modalAddView = inflater.inflate(R.layout.modal_addespecialidad, null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(modalAddView);
        dialog = builder.create();
        if (valor.equals("add")){
            addEspecialidad();
        }
        dialog.show();
    }

    private void addEspecialidad(){
        cleanForm();
        Button btnSubmit = modalAddView.findViewById(R.id.btnGuardar);
        EditText etIdEsp = modalAddView.findViewById(R.id.etIdEspecialidad);
        etIdEsp.setVisibility(View.GONE);
        EditText etNameEsp = modalAddView.findViewById(R.id.etEspecialidad);
        btnSubmit.setText("Crear");


        btnSubmit.setOnClickListener(view -> {
            String id = etIdEsp.getText().toString();
            String name = etNameEsp.getText().toString();
            if (!name.isEmpty()) {

                JsonObject especialidadData = new JsonObject();
                especialidadData.addProperty("var1", id);
                especialidadData.addProperty("var2", name);

                Log.d("API_REQUEST", "Datos a enviar a la API: " + especialidadData.toString());

                // Llamar a la API
                apiEspecialidad.saveEspecialidad(especialidadData).enqueue(new Callback<ResponseMessage<String>>() {
                    @Override
                    public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            ResponseMessage<String> responseMessage = response.body();
                            Toast.makeText(getContext(), "Nuevo registro creado:\n" + name + "\n", Toast.LENGTH_SHORT).show();
                            Log.e("API_RESPONSE", "Valor de var1: " + name);
                            dialog.dismiss(); // Cierra el modal
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
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
            ;
        });
    };

    private void editEspecialidad(@NonNull especialidad obj){
        showFormModal("edit");
        Button btnSubmit = modalAddView.findViewById(R.id.btnGuardar);
        TextView etIdEsp = modalAddView.findViewById(R.id.etIdEspecialidad);
        TextView etNameEsp = modalAddView.findViewById(R.id.etEspecialidad);

        etIdEsp.setText(obj.getIdEspecialidad());
        etIdEsp.setVisibility(View.VISIBLE);
        etIdEsp.setEnabled(false);
        etNameEsp.setText(obj.getEspecialidad());
        btnSubmit.setText("Actualizar");
        btnSubmit.setOnClickListener(view -> {
            String id = etIdEsp.getText().toString();
            String name = etNameEsp.getText().toString();
            if (!name.isEmpty()) {

                JsonObject especialidadData = new JsonObject();
                especialidadData.addProperty("var1", id);
                especialidadData.addProperty("var2", name);

                Log.d("API_REQUEST", "Datos a enviar a la API para editar: " + especialidadData.toString());
                apiEspecialidad.saveEspecialidad(especialidadData).enqueue(new Callback<ResponseMessage<String>>() {
                    @Override
                    public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            ResponseMessage<String> responseMessage = response.body();
                            Toast.makeText(getContext(), "Registro actualizado:\n" + name + "\n", Toast.LENGTH_SHORT).show();
                            Log.e("API_RESPONSE", "Valor de var1: " + name);
                            dialog.dismiss(); // Cierra el modal
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
            } else {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            };
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Se llama cuando el usuario presiona "Enter" en el teclado
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Se llama cuando el texto de la búsqueda cambia
                loadByValue(newText);
                return true;
            }
        });
    }

    private void loadAll() {
        Call<ResponseMessage<especialidad[]>> call = apiEspecialidad.getAll();
        call.enqueue(new Callback<ResponseMessage<especialidad[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<especialidad[]>> call, Response<ResponseMessage<especialidad[]>> response) {

                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    //ResponseMessage<especialidad[]> beans = response.body().getData();
                    ResponseMessage<especialidad[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterEspecialidad = new ViewEspecialidadAdapter(beans, obj -> {
                        adapterEspecialidad = new ViewEspecialidadAdapter(beans, new ViewEspecialidadAdapter.OnItemClickListener() {
                            // Aquí obtendrás los detalles del medico cuando se haga clic en una especialidad
                            @Override
                            public void onItemClick(especialidad obj) {
                                loadMedico(obj.getIdEspecialidad());
                                Log.d("ViewEspecialidadAdapter", "Clic en: " + obj.getIdEspecialidad() + " y " + obj.getEspecialidad());
                            }

                            // Editar
                            @Override
                            public void onEditClick(especialidad obj) {
                                Log.d("ViewEspecialidadAdapter", "Clic en editar: " + obj.getIdEspecialidad());
                                editEspecialidad(obj);
                            }

                            // Eliminar
                            @Override
                            public void onDeleteClick(String var1) {
                                Log.d("ViewEspecialidadAdapter", "Clic en eliminar: " + var1);
                                eliminarEspecialidad(var1);  // Llamar al método para eliminar la cita usando el idCita
                            }
                        });
                        recyclerEspecialidad.setAdapter(adapterEspecialidad);
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                    } else {
                        // Si no hay resultados, muestra un Toast
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerMedico.setVisibility(View.GONE);
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                        // Limpiar el adaptador si no hay resultados
                        /*if (adapterEspecialidad != null) {
                            loadAll();
                        }*/
                    }
                } else {
                    //Log.e("ViewEspecialidadFragment", "Error en la respuesta: " + response.message());
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerMedico.setVisibility(View.GONE);
                    recyclerCita.setVisibility(View.GONE);
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<especialidad[]>> call, Throwable t) {
                // Manejar el error
                //Log.e("ViewEspecialidadFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerMedico.setVisibility(View.GONE);
                recyclerCita.setVisibility(View.GONE);
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void loadByValue(String var2) {
        Call<ResponseMessage<especialidad[]>> call = apiEspecialidad.getByValue(var2);
        call.enqueue(new Callback<ResponseMessage<especialidad[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<especialidad[]>> call, Response<ResponseMessage<especialidad[]>> response) {

                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<especialidad[]> beans = response.body();
                    //if (beans != null && beans.getData().length > 0) {
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        // Si hay resultados, actualiza el adaptador
                        //adapterEspecialidad = new ViewEspecialidadAdapter(beans, obj -> {
                        adapterEspecialidad = new ViewEspecialidadAdapter(beans, new ViewEspecialidadAdapter.OnItemClickListener() {
                            // Este es el listener para cargar detalles del medico
                            @Override
                            public void onItemClick(especialidad obj) {
                                loadMedico(obj.getIdEspecialidad());
                                Log.d("ViewEspecialidadAdapter", "Clic en: " + obj.getIdEspecialidad());
                            }

                            // Editar
                            @Override
                            public void onEditClick(especialidad obj) {
                                Log.d("ViewEspecialidadAdapter", "Clic en editar: " + obj.getIdEspecialidad());
                                editEspecialidad(obj);
                            }

                            // Eliminar
                            @Override
                            public void onDeleteClick(String var1) {
                                eliminarEspecialidad(var1);  // Llamar al método para eliminar la cita usando el idCita
                            }
                        });
                        recyclerEspecialidad.setAdapter(adapterEspecialidad);
                        recyclerMedico.setVisibility(View.GONE);
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                    } else {
                        // Si no hay resultados, muestra un Toast
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerMedico.setVisibility(View.GONE);
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                        // Limpiar el adaptador si no hay resultados
                        /*if (adapterEspecialidad != null) {
                            loadAll();
                        }*/
                    }
                } else {
                    //Log.e("ViewEspecialidadFragment", "Error en la respuesta: " + response.message());
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerMedico.setVisibility(View.GONE);
                    recyclerCita.setVisibility(View.GONE);
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<especialidad[]>> call, Throwable t) {
                // Manejar el error
                //Log.e("ViewEspecialidadFragment", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerMedico.setVisibility(View.GONE);
                recyclerCita.setVisibility(View.GONE);
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void cleanForm(){
        TextView etIdEsp = modalAddView.findViewById(R.id.etIdEspecialidad);
        TextView etNameEsp = modalAddView.findViewById(R.id.etEspecialidad);
        etIdEsp.setText("");
        etNameEsp.setText("");
    }

    private void loadMedico(String var3) {
        // Aquí se realiza una llamada a la API para obtener los detalles del medico
        //Call<ResponseMessage<medico[]>> call = apiMedico.getByValue("E001");
        Call<ResponseMessage<medico[]>> call = apiMedico.getByEspecialidad(var3);
        call.enqueue(new Callback<ResponseMessage<medico[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<medico[]>> call, Response<ResponseMessage<medico[]>> response) {

                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    // Actualizar el RecyclerView de medicos con los detalles
                    ResponseMessage<medico[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterMedico = new ViewMedicoAdapter(beans, obj -> {
                        adapterMedico = new ViewMedicoAdapter(beans, new ViewMedicoAdapter.OnItemClickListener() {
                            // Este es el listener para cargar detalles del medico
                            @Override
                            public void onItemClick(medico obj) {
                                loadCita(obj.getIdMedico());
                                Log.d("ViewMedicoAdapter", "Clic en: " + obj.getIdMedico());
                            }

                            // Editar
                            @Override
                            public void onEditClick(medico obj) {
                                //Log.d("ViewMedicoAdapter", "Clic en editar: " + obj.getIdMedico());
                                //editar(obj);
                            }

                            // Eliminar
                            @Override
                            public void onDeleteClick(String var1) {
                                //Log.d("ViewMedicoAdapter", "Clic en eliminar: " + var1);
                                //eliminarMedico(var1);  // Llamar al método para eliminar la cita usando el idCita
                            }
                        });
                        recyclerMedico.setAdapter(adapterMedico);
                        recyclerMedico.setVisibility(View.VISIBLE);
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerCita.setVisibility(View.GONE);
                        recyclerPaciente.setVisibility(View.GONE);
                    }
                } else {
                    //Log.e("ViewEspecialidadFragment", "Error en la respuesta: " + response.message());
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerCita.setVisibility(View.GONE);
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<medico[]>> call, Throwable t) {
                //Log.e("ViewEspecialidadFragment", "Error al buscar medico: " + t.getMessage());
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerCita.setVisibility(View.GONE);
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void loadCita(String var3) {
        // Aquí se realiza una llamada a la API para obtener los detalles del cita
        //Call<ResponseMessage<cita[]>> call = apiCita.getByValue("''", "M035");
        Call<ResponseMessage<cita[]>> call = apiCita.getByValue("''", var3, "''");
        call.enqueue(new Callback<ResponseMessage<cita[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<cita[]>> call, Response<ResponseMessage<cita[]>> response) {

                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    // Actualizar el RecyclerView de citas con los detalles
                    ResponseMessage<cita[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterCita = new ViewCitaAdapter(beans, obj -> {
                        adapterCita = new ViewCitaAdapter(beans, new ViewCitaAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(cita obj) {
                                // Este es el listener para cargar detalles del medico
                                loadPaciente(obj.getIdCita());
                                Log.d("ViewCitaAdapter", "Clic en: " + obj.getIdCita());
                            }

                            @Override
                            public void onEditClick(cita obj) {
                                //Log.d("ViewCitaAdapter", "Clic en editar: " + obj.getIdCita());
                                //editar(obj);
                            }

                            @Override
                            public void onDeleteClick(String var1) {
                                //Log.d("ViewCitaAdapter", "Clic en eliminar: " + var1);
                                //eliminarCita(var1);  // Llamar al método para eliminar la cita usando el idCita
                            }
                        });
                        recyclerCita.setAdapter(adapterCita);
                        recyclerCita.setVisibility(View.VISIBLE);
                        recyclerPaciente.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerPaciente.setVisibility(View.GONE);
                    }
                } else {
                    //Log.e("ViewEspecialidadFragment", "Error en la respuesta: " + response.message());
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<cita[]>> call, Throwable t) {
                //Log.e("ViewEspecialidadFragment", "Error al buscar cita: " + t.getMessage());
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void loadPaciente(String var3) {
        // Aquí se realiza una llamada a la API para obtener los detalles del paciente
        //Call<ResponseMessage<paciente[]>> call = apiPaciente.getByValue("''", "M035");
        Call<ResponseMessage<paciente[]>> call = apiPaciente.getByCita(var3);
        call.enqueue(new Callback<ResponseMessage<paciente[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<paciente[]>> call, Response<ResponseMessage<paciente[]>> response) {

                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    // Actualizar el RecyclerView de pacientes con los detalles
                    ResponseMessage<paciente[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        adapterPaciente = new ViewPacienteAdapter(beans, null);
                        recyclerPaciente.setAdapter(adapterPaciente);
                        recyclerPaciente.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerPaciente.setVisibility(View.GONE);
                    }
                } else {
                    //Log.e("ViewEspecialidadFragment", "Error en la respuesta: " + response.message());
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<paciente[]>> call, Throwable t) {
                //Log.e("ViewEspecialidadFragment", "Error al buscar paciente: " + t.getMessage());
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void eliminarEspecialidad(String var1) {
        Call<ResponseMessage<String>> call = apiEspecialidad.deleteEspecialidad(var1);
        call.enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Eliminacion con éxito", Toast.LENGTH_SHORT).show();
                    loadAll();
                } else {
                    Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
