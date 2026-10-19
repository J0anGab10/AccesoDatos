package org.example;

import jakarta.xml.bind.annotation.*;

// Anotaciones de la clase
@XmlRootElement(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)

public class Usuario {
    @XmlAttribute
    int id;
    String nombre;
    String email;
    String rol;

    public Usuario() {

    }

    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public String getRol() {
        return rol;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
