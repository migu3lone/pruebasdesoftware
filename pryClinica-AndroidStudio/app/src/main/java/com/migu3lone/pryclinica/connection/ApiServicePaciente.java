package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.paciente;

public interface ApiServicePaciente {
    // Obtener todos los regristos
    @GET("apiPaciente.php")
    Call<ResponseMessage<paciente[]>> getAll();

    // Obtener por ID
    @GET("apiPaciente.php")
    Call<ResponseMessage<paciente[]>> getById(@Query("var1") String var1);

    //where Apellidos like ?
    @GET("apiPaciente.php")
    Call<ResponseMessage<paciente[]>> getByValue(@Query("var2") String var2);

    //where IdCita like ?
    @GET("apiPaciente.php")
    Call<ResponseMessage<paciente[]>> getByCita(@Query("var3") String var3);

    // Crear o actualizar
    @POST("apiPaciente.php")
    Call<ResponseMessage<String>> savePaciente(@Body JsonObject pacienteData);

    // Eliminar por ID
    @DELETE("apiPaciente.php")
    Call<ResponseMessage<String>> deletePaciente(@Query("var1") String var1);
}
