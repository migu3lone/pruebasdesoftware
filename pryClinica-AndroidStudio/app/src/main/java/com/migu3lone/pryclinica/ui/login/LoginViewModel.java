package com.migu3lone.pryclinica.ui.login;

import android.util.Log;
import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.migu3lone.pryclinica.R;
import com.migu3lone.pryclinica.beans.usuario;
import com.migu3lone.pryclinica.connection.ApiServiceLogin;
import com.migu3lone.pryclinica.connection.ResponseMessage;
import com.migu3lone.pryclinica.connection.RetrofitClient;
import com.migu3lone.pryclinica.data.LoginRepository;
import com.migu3lone.pryclinica.data.model.LoggedInUser;
import com.migu3lone.pryclinica.data.Result;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private ApiServiceLogin apiLogin;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        JsonObject loginData = new JsonObject();
        loginData.addProperty("username", username); // id del usuario (IdMedico, IdPaciente, IdUsuario)
        loginData.addProperty("password", password); // Contraseña

        // Obtener cliente de Retrofit y el servicio de la API
        ApiServiceLogin apiServiceLogin = RetrofitClient.getClient().create(ApiServiceLogin.class);
        //Result<LoggedInUser> result = loginRepository.login(username, password);

        // Llamada a la API
        Call<ResponseMessage<usuario[]>> call = apiServiceLogin.login(loginData);
        call.enqueue(new Callback<ResponseMessage<usuario[]>>() {
            @Override
            public void onResponse(Call<ResponseMessage<usuario[]>> call, Response<ResponseMessage<usuario[]>> response) {

                //if (result instanceof Result.Success) {
                //if (response.isSuccessful()) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<usuario[]> beans = response.body();
                    //if (beans.getStatus() == 200) {
                    if (beans.getStatus() == 200 && beans.getData() != null && beans.getData().length > 0) {
                        // Autenticación exitosa
                        usuario user = beans.getData()[0];
                        //usuario[] user = beans.getData();
                        if (user != null) {
                            //loginResult.setValue(new LoginResult(new LoggedInUserView(user.getApellidos(), "paciente")));
                            //loginResult.setValue(new LoginResult(new LoggedInUserView(user.getIdUsuario())));
                            loginResult.setValue(new LoginResult(new LoggedInUserView(user.getApellidos(), user.getRol())));
                            //loginResult.setValue(new LoginResult(new LoggedInUserView(responseMessage.getData().getApellidos())));
                            //loginResult.setValue(new LoginResult(new LoggedInUserView(user.getApellidos())));
                        } else {
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                        /*LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        loginResult.setValue(new LoginResult(new LoggedInUserView(responseMessage.getData().getApellidos())));
                        //loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));*/
                    } else {
                        // Credenciales incorrectas
                        Log.e("LoginViewModel", "Credenciales incorrectas o datos vacíos");
                        loginResult.setValue(new LoginResult(R.string.login_failed));
                    }
                } else {
                    // Error en la respuesta
                    Log.e("LoginViewModel", "Error HTTP: " + response.code());
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<usuario[]>> call, Throwable t) {
                // Error en la conexión o la llamada a la API
                Log.e("LoginViewModel", "Error en la llamada a la API: " + t.getMessage());
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}