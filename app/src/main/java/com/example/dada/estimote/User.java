package com.example.dada.estimote;

/**
 * Created by dada on 2017/8/21.
 */

class User {
    public String id;
    public String password;
    public String name;
    public String birthday;

    public User(){

    }

    public User( String password,String name, String birthday) {
        this.password = password;
        this.name = name;
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setId(String id) {
        this.id = id;
    }

}
