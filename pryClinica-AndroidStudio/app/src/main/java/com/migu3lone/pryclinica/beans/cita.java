package com.migu3lone.pryclinica.beans;

import java.io.Serializable;

public class cita implements Serializable {
    private String IdCita;
    private String Paciente;
    private String FK_Especialidad;
    private String FK_Medico;
    private String Fecha;
    private String Hora;

    private String FK_Paciente;

    public String horax() {
        if (Hora != null && Hora.length() > 5) {
            return Hora.substring(0, 5);
        }
        return Hora;
    }

    public cita(String idCita, String paciente, String FK_Especialidad, String FK_Medico, String fecha, String hora, String FK_Paciente) {
        IdCita = idCita;
        Paciente = paciente;
        this.FK_Especialidad = FK_Especialidad;
        this.FK_Medico = FK_Medico;
        Fecha = fecha;
        Hora = hora;
        this.FK_Paciente = FK_Paciente;
    }

    public String getIdCita() {
        return IdCita;
    }

    public void setIdCita(String idCita) {
        IdCita = idCita;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getFK_Especialidad() {
        return FK_Especialidad;
    }

    public void setFK_Especialidad(String FK_Especialidad) {
        this.FK_Especialidad = FK_Especialidad;
    }

    public String getFK_Medico() {
        return FK_Medico;
    }

    public void setFK_Medico(String FK_Medico) {
        this.FK_Medico = FK_Medico;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFK_Paciente() {
        return FK_Paciente;
    }

    public void setFK_Paciente(String FK_Paciente) {
        this.FK_Paciente = FK_Paciente;
    }
}
