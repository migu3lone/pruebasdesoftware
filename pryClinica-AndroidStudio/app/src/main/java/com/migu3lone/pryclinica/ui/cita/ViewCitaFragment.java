package com.migu3lone.pryclinica.ui.cita;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.migu3lone.pryclinica.R;
import com.migu3lone.pryclinica.beans.cita;
import com.migu3lone.pryclinica.beans.paciente;
import com.migu3lone.pryclinica.connection.ApiServiceCita;
import com.migu3lone.pryclinica.connection.ApiServicePaciente;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;
import com.migu3lone.pryclinica.ui.adapter.ViewCitaAdapter;
import com.migu3lone.pryclinica.ui.adapter.ViewPacienteAdapter;

public class ViewCitaFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView recyclerCita;
    private RecyclerView recyclerPaciente;
    private ViewCitaAdapter adapterCita;
    private ViewPacienteAdapter adapterPaciente;
    private ApiServiceCita apiCita;
    private ApiServicePaciente apiPaciente;

    public ViewCitaFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcita, container, false);

        searchView = view.findViewById(R.id.searchView);
        setupSearchView();

        recyclerCita = view.findViewById(R.id.recyclerCita);
        recyclerCita.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerPaciente = view.findViewById(R.id.recyclerPaciente);
        recyclerPaciente.setLayoutManager(new LinearLayoutManager(getContext()));

        apiCita = RetrofitClient.getClient().create(ApiServiceCita.class);
        apiPaciente = RetrofitClient.getClient().create(ApiServicePaciente.class);

        loadAll();
        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadByValue(newText);
                return true;
            }
        });
    }

    private void loadAll() {
        Call<ResponseMessage<cita[]>> call = apiCita.getAll();
        call.enqueue(new Callback<ResponseMessage<cita[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<cita[]>> call, Response<ResponseMessage<cita[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<cita[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterCita = new ViewCitaAdapter(beans, obj -> {
                        adapterCita = new ViewCitaAdapter(beans, new ViewCitaAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(cita obj) {
                                loadPaciente(obj.getFK_Paciente());
                                Log.d("ViewCitaAdapter", "Clic en: " + obj.getFK_Paciente());
                            }

                            @Override
                            public void onEditClick(cita obj) {
                                Log.d("ViewCitaAdapter", "Clic en editar: " + obj.getIdCita());
                                //editar(obj);
                            }

                            @Override
                            public void onDeleteClick(String var1) {
                                Log.d("ViewCitaAdapter", "Clic en eliminar: " + var1);
                                eliminarCita(var1);
                            }
                        });
                        recyclerCita.setAdapter(adapterCita);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerPaciente.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<cita[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void loadByValue(String var2) {
        Call<ResponseMessage<cita[]>> call = apiCita.getByValue(var2, "''", "''");
        call.enqueue(new Callback<ResponseMessage<cita[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<cita[]>> call, Response<ResponseMessage<cita[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<cita[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        adapterCita = new ViewCitaAdapter(beans, new ViewCitaAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(cita obj) {
                                loadPaciente(obj.getFK_Paciente());
                                Log.d("ViewCitaAdapter", "Clic en: " + obj.getFK_Paciente());
                            }

                            @Override
                            public void onEditClick(cita obj) {
                                Log.d("ViewCitaAdapter", "Clic en editar: " + obj.getIdCita());
                                //editar(obj);
                            }

                            @Override
                            public void onDeleteClick(String var1) {
                                eliminarCita(var1);
                            }
                        });
                        recyclerCita.setAdapter(adapterCita);
                        recyclerPaciente.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerPaciente.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerPaciente.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<cita[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerPaciente.setVisibility(View.GONE);
            }
        });
    }

    private void loadPaciente(String var1) {
        Call<ResponseMessage<paciente[]>> call = apiPaciente.getById(var1);
        call.enqueue(new Callback<ResponseMessage<paciente[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<paciente[]>> call, Response<ResponseMessage<paciente[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<paciente[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        adapterPaciente = new ViewPacienteAdapter(beans, null);
                        recyclerPaciente.setAdapter(adapterPaciente);
                        recyclerPaciente.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<paciente[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarCita(String var1) {
        Call<ResponseMessage<String>> call = apiCita.deleteCita(var1);
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
