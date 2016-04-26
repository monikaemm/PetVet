package com.github.monikaemm;

/**
 * Created by Monika Hołysz on 26.04.2016.
 */


public class User {

    public enum Type {ADMINISTRATOR, DOCTOR, CLIENT, ANYBODY}

    Type type;

    public User(Type type) {

        this.type = type;
    }


}
