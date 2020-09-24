package org.acme;

import io.quarkus.funqy.Funq;

public class ExampleResource {
    @Funq("message")
    public String hello() {
        return "goodbye";
    }
}