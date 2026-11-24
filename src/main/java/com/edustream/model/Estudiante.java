package com.edustream.model;

public final class Estudiante extends Usuario {
  public Estudiante(int id, String nombre, String email) {
    super(id, nombre, email);
  }

  @Override
  public String getRol() {
    return "ESTUDIANTE";
  }

  @Override
  public boolean puedeGestionarCursos() {
    return false;
  }
}
