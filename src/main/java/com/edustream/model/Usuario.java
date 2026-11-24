package com.edustream.model;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
  private static final long serialVersionUID = 1L;
  private final int id;
  private final String nombre, email;

  protected Usuario(int id, String nombre, String email) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getEmail() {
    return email;
  }

  public abstract String getRol();

  public abstract boolean puedeGestionarCursos();
}
