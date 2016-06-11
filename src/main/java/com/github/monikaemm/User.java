package com.github.monikaemm;

/**
 * Created by Monika Hołysz on 26.04.2016.
 */


public class User {

    private String name;
    private String surname;
    private String log;
    private String password;
    private Type type;

    public enum Type {ADMINISTRATOR, DOCTOR, CLIENT}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String login) {
        this.log = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", log='" + log + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
