package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.espmed;
import com.migu3lone.pryclinica.beans.medico;

public interface ApiServiceEspmed {
    // Obtener todos los regristos
    @GET("apiEspmed.php")
    Call<ResponseMessage<espmed[]>> getAll();

    // Obtener por ID
    @GET("apiEspmed.php")
    Call<ResponseMessage<medico[]>> getById(@Query("var1") String var1);

    // Obtener por valor
    @GET("apiEspmed.php")
    Call<ResponseMessage<medico[]>> getByValue(@Query("var2") String var2);

    //Call<ResponseMessage<espmed[]>> call = apiEspmed.php.getByValue("12345"); // Ejemplo con "12345" como var1

    // Obtener por valor
    //@GET("apiEspmed.php")
    //Call<ResponseMessage<espmed[]>> getByValue(@Query("var2") String var2);

    // Crear o actualizar
    @POST("apiEspmed.php")
    Call<ResponseMessage<String>> saveEspmed(@Body JsonObject espmedData);

    // Eliminar por ID
    @DELETE("apiEspmed.php")
    Call<ResponseMessage<String>> deleteEspmed(@Query("var1") String var1);
}
