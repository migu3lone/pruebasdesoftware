package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.especialidad;

public interface ApiServiceEspecialidad {
    // Obtener todos los regristos
    @GET("apiEspecialidad.php")
    Call<ResponseMessage<especialidad[]>> getAll();

    // Obtener por ID
    @GET("apiEspecialidad.php")
    //Call<ResponseMessage<especialidad>> getById(@Path("var1") String var1);
    //Call<ResponseMessage<especialidad>> getById(@Query("var1") String var1);
    Call<ResponseMessage<especialidad[]>> getById(@Query("var1") String var1);

    // Obtener por valor
    @GET("apiEspecialidad.php")
    Call<ResponseMessage<especialidad[]>> getByValue(@Query("var2") String var2);

    // Crear
    @POST("apiEspecialidad.php")
    Call<ResponseMessage<String>> saveEspecialidad(@Body JsonObject especialidadData);

    // Eliminar por ID
    @DELETE("apiEspecialidad.php")
    Call<ResponseMessage<String>> deleteEspecialidad(@Query("var1") String var1);
}
