package com.migu3lone.pryclinica.beans;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class usuario implements Serializable {
    private String idUsuario;
    private String Password;
    private String Rol;
    private String Apellidos;
    private String Nombres;
    private String Correo;

    public usuario(String idUsuario, String password, String rol, String apellidos, String nombres, String correo, String username) {
        this.idUsuario = idUsuario;
        Password = password;
        Rol = rol;
        Apellidos = apellidos;
        Nombres = nombres;
        Correo = correo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }


}
