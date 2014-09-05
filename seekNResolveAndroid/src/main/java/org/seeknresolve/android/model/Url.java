package org.seeknresolve.android.model;

import com.j256.ormlite.field.DatabaseField;

public class Url {
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(unique = true)
    private String url;

    @DatabaseField
    private String lastLogin;

    public Url() {

    }

    public Url(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return url;
    }
}
