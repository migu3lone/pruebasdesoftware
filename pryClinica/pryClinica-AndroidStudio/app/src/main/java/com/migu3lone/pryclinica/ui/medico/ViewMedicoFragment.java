package com.migu3lone.pryclinica.ui.medico;

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
import com.migu3lone.pryclinica.beans.medico;
import com.migu3lone.pryclinica.beans.cita;
import com.migu3lone.pryclinica.connection.ApiServiceMedico;
import com.migu3lone.pryclinica.connection.ApiServiceCita;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;
import com.migu3lone.pryclinica.ui.adapter.ViewMedicoAdapter;
import com.migu3lone.pryclinica.ui.adapter.ViewCitaAdapter;

public class ViewMedicoFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView recyclerMedico;
    private RecyclerView recyclerCita;
    private ViewMedicoAdapter adapterMedico;
    private ViewCitaAdapter adapterCita;
    private ApiServiceMedico apiMedico;
    private ApiServiceCita apiCita;

    public ViewMedicoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewmedico, container, false);

        searchView = view.findViewById(R.id.searchView);
        setupSearchView();

        recyclerMedico = view.findViewById(R.id.recyclerMedico);
        recyclerMedico.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerCita = view.findViewById(R.id.recyclerCita);
        recyclerCita.setLayoutManager(new LinearLayoutManager(getContext()));

        apiMedico = RetrofitClient.getClient().create(ApiServiceMedico.class);
        apiCita = RetrofitClient.getClient().create(ApiServiceCita.class);

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
        Call<ResponseMessage<medico[]>> call = apiMedico.getAll();
        call.enqueue(new Callback<ResponseMessage<medico[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<medico[]>> call, Response<ResponseMessage<medico[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<medico[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterMedico = new ViewMedicoAdapter(beans, obj -> {
                        adapterMedico = new ViewMedicoAdapter(beans, new ViewMedicoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(medico obj) {
                                loadCita(obj.getIdMedico());
                                Log.d("ViewMedicoAdapter", "Clic en: " + obj.getIdMedico());
                            }

                            @Override
                            public void onEditClick(medico obj) {
                                Log.d("ViewMedicoAdapter", "Clic en editar: " + obj.getIdMedico());
                                //editar(obj);
                            }

                            @Override
                            public void onDeleteClick(String var1) {
                                Log.d("ViewMedicoAdapter", "Clic en eliminar: " + var1);
                                eliminarMedico(var1);
                            }
                        });
                        recyclerMedico.setAdapter(adapterMedico);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerCita.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerCita.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<medico[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerCita.setVisibility(View.GONE);
            }
        });
    }

    private void loadByValue(String var2) {
        Call<ResponseMessage<medico[]>> call = apiMedico.getByValue(var2);
        call.enqueue(new Callback<ResponseMessage<medico[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<medico[]>> call, Response<ResponseMessage<medico[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<medico[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        //adapterMedico = new ViewMedicoAdapter(beans, obj -> {
                        adapterMedico = new ViewMedicoAdapter(beans, new ViewMedicoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(medico obj) {
                                loadCita(obj.getIdMedico());
                                Log.d("ViewMedicoAdapter", "Clic en: " + obj.getIdMedico());
                            }

                            @Override
                            public void onEditClick(medico obj) {
                                Log.d("ViewMedicoAdapter", "Clic en editar: " + obj.getIdMedico());
                                //editar(obj);
                            }

                            @Override
                            public void onDeleteClick(String var1) {
                                eliminarMedico(var1);
                            }
                        });
                        recyclerMedico.setAdapter(adapterMedico);
                        recyclerCita.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                        recyclerCita.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                    recyclerCita.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<medico[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
                recyclerCita.setVisibility(View.GONE);
            }
        });
    }

    private void loadCita(String var3) {
        Call<ResponseMessage<cita[]>> call = apiCita.getByValue("''", var3, "''");
        call.enqueue(new Callback<ResponseMessage<cita[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<cita[]>> call, Response<ResponseMessage<cita[]>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<cita[]> beans = response.body();
                    if (beans != null && beans.getData() != null && beans.getData().length > 0) {
                        adapterCita = new ViewCitaAdapter(beans, null);
                        recyclerCita.setAdapter(adapterCita);
                        recyclerCita.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "No hay datos disponibles después de una respuesta exitosa: ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "La respuesta no fue exitosa: ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<cita[]>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de fallo en la conexión / solicitud: ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarMedico(String var1) {
        Call<ResponseMessage<String>> call = apiMedico.deleteMedico(var1);
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
