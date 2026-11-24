package com.edustream.model;

public final class Curso {
  private final int id, idProfesor;
  private final String nombre, descripcion, profesor;

  public Curso(int id, String nombre, String descripcion, int idProfesor, String profesor) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.idProfesor = idProfesor;
    this.profesor = profesor;
  }

  public int getId() {
    return id;
  }

  public int getIdProfesor() {
    return idProfesor;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getProfesor() {
    return profesor;
  }
}
