package com.edustream.model;

import java.time.LocalDate;

public final class Inscripcion {
  private final int id, idCurso;
  private final String curso, estudiante;
  private final LocalDate fecha;

  public Inscripcion(int id, int idCurso, String curso, String estudiante, LocalDate fecha) {
    this.id = id;
    this.idCurso = idCurso;
    this.curso = curso;
    this.estudiante = estudiante;
    this.fecha = fecha;
  }

  public int getId() {
    return id;
  }

  public int getIdCurso() {
    return idCurso;
  }

  public String getCurso() {
    return curso;
  }

  public String getEstudiante() {
    return estudiante;
  }

  public LocalDate getFecha() {
    return fecha;
  }
}
