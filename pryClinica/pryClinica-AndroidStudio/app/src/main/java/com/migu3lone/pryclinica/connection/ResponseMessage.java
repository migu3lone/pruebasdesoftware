package com.migu3lone.pryclinica.connection;

import android.content.Context;
import java.util.List;

public class ResponseMessage<T> {
    private int status;
    private String mensaje;
    private T data;
    private boolean success;

    public ResponseMessage(int status, String mensaje, T data) {
        this.status = status;
        this.mensaje = mensaje;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public boolean getSuccess() { return success; }
}
