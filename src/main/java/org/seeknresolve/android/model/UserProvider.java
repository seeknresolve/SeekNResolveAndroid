package org.seeknresolve.android.model;

import javax.inject.Inject;

public class UserProvider {
    User loggedUser;

    public UserProvider() {

    }

    public UserProvider(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
