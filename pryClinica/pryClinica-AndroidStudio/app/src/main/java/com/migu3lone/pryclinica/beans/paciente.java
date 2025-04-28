package com.migu3lone.pryclinica.beans;

import java.io.Serializable;

public class paciente implements Serializable {
    private String IdPaciente;
    private String Password;
    private String Apellidos;
    private String Nombres;
    private String DNI;
    private String Sexo;
    private String Nacimiento;
    private String Correo;
    private String Direccion;
    private String Celular;

    public String sexox() {
        return Sexo.equals("M") ? "Masculino" : "Femenino";
    }

    public String pacientex() {
        return Apellidos+", "+Nombres;
    }

    public paciente(String idPaciente, String password, String apellidos, String nombres, String DNI, String sexo, String nacimiento, String correo, String direccion, String celular) {
        IdPaciente = idPaciente;
        Password = password;
        Apellidos = apellidos;
        Nombres = nombres;
        this.DNI = DNI;
        Sexo = sexo;
        Nacimiento = nacimiento;
        Correo = correo;
        Direccion = direccion;
        Celular = celular;
    }

    public String getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        IdPaciente = idPaciente;
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

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getNacimiento() {
        return Nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        Nacimiento = nacimiento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
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
