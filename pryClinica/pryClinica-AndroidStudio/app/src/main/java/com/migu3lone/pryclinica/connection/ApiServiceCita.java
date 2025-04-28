package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.cita;

public interface ApiServiceCita {
    // Obtener todos los regristos
    @GET("apiCita.php")
    Call<ResponseMessage<cita[]>> getAll();

    // Obtener por ID
    @GET("apiCita.php")
    Call<ResponseMessage<cita[]>> getById(@Query("var1") String var1);

    // where especialidad.Especialidad like ? or fk_Medico like ? or fk_Paciente like ?
    @GET("apiCita.php")
    Call<ResponseMessage<cita[]>> getByValue(@Query("var2") String var2, @Query("var3") String var3, @Query("var4") String var4);
    //Call<ResponseMessage<cita[]>> getByValue(@Query("var2") String var2, @Query("var3") String var3);
    //Call<ResponseMessage<cita[]>> getByValue(@Query("var2") String var2);

    // Crear o actualizar
    @POST("apiCita.php")
    Call<ResponseMessage<String>> saveCita(@Body JsonObject citaData);

    // Eliminar por ID
    @DELETE("apiCita.php")
    Call<ResponseMessage<String>> deleteCita(@Query("var1") String var1);
}
