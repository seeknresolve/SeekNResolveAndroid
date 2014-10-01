package org.seeknresolve.android.rest;

public class SnrResponse<T> {
    T object;
    String status;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
