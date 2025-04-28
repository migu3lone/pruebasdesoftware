package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.medico;

public interface ApiServiceMedico {
    // Obtener todos los regristos
    @GET("apiMedico.php")
    Call<ResponseMessage<medico[]>> getAll();

    // Obtener por ID
    @GET("apiMedico.php")
    Call<ResponseMessage<medico[]>> getById(@Query("var1") String var1);

    // where DNI like ?
    @GET("apiMedico.php")
    Call<ResponseMessage<medico[]>> getByValue(@Query("var2") String var2);

    // Obtener por especialidad
    @GET("apiMedico.php")
    Call<ResponseMessage<medico[]>> getByEspecialidad(@Query("var3") String var3);

    // Crear o actualizar
    @POST("apiMedico.php")
    Call<ResponseMessage<String>> saveMedico(@Body JsonObject medicoData);

    // Eliminar por ID
    @DELETE("apiMedico.php")
    Call<ResponseMessage<String>> deleteMedico(@Query("var1") String var1);
}
