package com.migu3lone.pryclinica.beans;

import java.io.Serializable;

public class especialidad implements Serializable {
    private String IdEspecialidad;
    private String Especialidad;

    public especialidad(String idEspecialidad, String especialidad) {
        IdEspecialidad = idEspecialidad;
        Especialidad = especialidad;
    }

    public String getIdEspecialidad() {
        return IdEspecialidad;
    }

    public void setIdEspecialidad(String idEspecialidad) {
        IdEspecialidad = idEspecialidad;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setEspecialidad(String especialidad) {
        Especialidad = especialidad;
    }
}
