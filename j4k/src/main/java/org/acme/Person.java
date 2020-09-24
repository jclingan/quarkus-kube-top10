package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Person extends PanacheEntity {
    public String name;
    public int age;
}
