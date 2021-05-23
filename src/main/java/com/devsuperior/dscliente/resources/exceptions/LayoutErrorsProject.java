package com.devsuperior.dscliente.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

// Essa classe eh usada para montar a "cara" das mensagens de erro do projeto "dscliente"
public class LayoutErrorsProject implements Serializable {
    //-----------------------------------------------
    // Atributos e id do serializable
    //------------------------------------------------
    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    //-----------------------------------------------
    // Construtores
    //------------------------------------------------
    public LayoutErrorsProject() {
    }

    public LayoutErrorsProject(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    //-----------------------------------------------
    // Getters and Setters
    //------------------------------------------------

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
