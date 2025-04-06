package com.migu3lone.pryclinica.beans;

import java.io.Serializable;

public class espmed implements Serializable {
    private String IdEspmed;
    private String FK_Especialidad;
    private String FK_Medico;

    public espmed(String idEspmed, String FK_Especialidad, String FK_Medico) {
        IdEspmed = idEspmed;
        this.FK_Especialidad = FK_Especialidad;
        this.FK_Medico = FK_Medico;
    }

    public String getIdEspmed() {
        return IdEspmed;
    }

    public void setIdEspmed(String idEspmed) {
        IdEspmed = idEspmed;
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
}
