package com.migu3lone.pryclinica.beans;

import java.io.Serializable;

public class medico implements Serializable {
    private String IdMedico;
    private String Password;
    private String Apellidos;
    private String Nombres;
    private String DNI;
    private String CMP;
    private String Direccion;
    private String Celular;

    public String medicox() {
        return Apellidos+", "+Nombres;
    }

    public medico(String idMedico, String password, String apellidos, String nombres, String DNI, String CMP, String direccion, String celular) {
        IdMedico = idMedico;
        Password = password;
        Apellidos = apellidos;
        Nombres = nombres;
        this.DNI = DNI;
        this.CMP = CMP;
        Direccion = direccion;
        Celular = celular;
    }

    public String getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(String idMedico) {
        IdMedico = idMedico;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCMP() {
        return CMP;
    }

    public void setCMP(String CMP) {
        this.CMP = CMP;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
