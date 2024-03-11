package com.k3project.demo.service.dto;

public class LoginDTO {
    private String firstName;
    private String password;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDTO(String lastName, String password) {
        this.firstName = lastName;
        this.password = password;
    }
}
