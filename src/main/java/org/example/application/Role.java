package org.example.application;

import lombok.Getter;

import javax.annotation.processing.RoundEnvironment;

@Getter
public class Role {
    private Integer id;
    private String name;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
