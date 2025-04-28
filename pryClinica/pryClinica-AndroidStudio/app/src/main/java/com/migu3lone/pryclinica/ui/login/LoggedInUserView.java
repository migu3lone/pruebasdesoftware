package com.migu3lone.pryclinica.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String displayRol;

    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String displayRol) {
        this.displayName = displayName;
        this.displayRol = displayRol;
    }

    String getDisplayName() {
        return displayName;
    }

    String getDisplayRol() {
        return displayRol;
    }
}