package com.k3project.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//TODO nefunguje lombok
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}


