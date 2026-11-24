package com.edustream.service;

import com.edustream.dao.*;
import com.edustream.exception.*;
import com.edustream.model.Usuario;
import java.sql.SQLException;

public final class InscripcionService {
  private final InscripcionDAO dao = new InscripcionDAO();

  private void estudiante(Usuario u) throws NegocioException {
    if (u == null || u.puedeGestionarCursos())
      throw new NegocioException("Solo los estudiantes pueden inscribirse.");
  }

  public void inscribir(int curso, Usuario u) throws SQLException, NegocioException {
    estudiante(u);
    if (new CursoDAO().buscar(curso) == null)
      throw new NegocioException("El curso ya no está disponible.");
    dao.crear(u.getId(), curso);
  }

  public void cancelar(int id, Usuario u) throws SQLException, NegocioException {
    estudiante(u);
    if (!dao.cancelar(id, u.getId()))
      throw new NegocioException("La inscripción no existe o no te pertenece.");
  }
}
