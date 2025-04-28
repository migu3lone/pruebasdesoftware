package com.migu3lone.pryclinica.connection;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.migu3lone.pryclinica.beans.usuario;

public interface ApiServiceLogin {

    @POST("apiLogin.php") // Este es el endpoint al que se hace la solicitud
    Call<ResponseMessage<usuario[]>> login(@Body JsonObject loginData);

    /*// Crear o actualizar
    @POST("apiLogin.php") // Este es el endpoint al que se hace la solicitud
    Call<ResponseMessage<String>> login(@Body JsonObject loginData);
    // all<LoginResponse> login(@Body LoginRequest loginRequest);


    // Método para realizar el login, enviando el usuario y la contraseña como un JSON
    /*@POST("apiLogin.php")  // URL del endpoint, suponiendo que la ruta del API es "/login"
    Call<ResponseMessage> login(@Body LoginRequest loginRequest);




    // Endpoint para iniciar sesión
    @POST("apiLogin.php")
    Call<ResponseMessage<usuario>> login(@Body JsonObject loginData);*/
}
