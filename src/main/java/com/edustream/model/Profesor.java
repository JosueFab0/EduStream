package com.edustream.model;

public final class Profesor extends Usuario {
  public Profesor(int id, String nombre, String email) {
    super(id, nombre, email);
  }

  @Override
  public String getRol() {
    return "PROFESOR";
  }

  @Override
  public boolean puedeGestionarCursos() {
    return true;
  }
}
