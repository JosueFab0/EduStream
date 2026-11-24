package com.edustream.service;

import com.edustream.dao.CursoDAO;
import com.edustream.exception.NegocioException;
import com.edustream.model.*;
import java.sql.SQLException;
import java.util.List;

public final class CursoService implements ICursoOperaciones {
  private final CursoDAO dao = new CursoDAO();

  private void profesor(Usuario u) throws NegocioException {
    if (u == null || !u.puedeGestionarCursos())
      throw new NegocioException("Solo los profesores pueden gestionar cursos.");
  }

  @Override
  public List<Curso> listar(Usuario u) throws SQLException {
    return dao.listar(u.puedeGestionarCursos() ? u.getId() : null);
  }

  @Override
  public Curso buscarParaEditar(int id, Usuario u) throws SQLException, NegocioException {
    profesor(u);
    Curso c = dao.buscar(id);
    if (c == null || c.getIdProfesor() != u.getId())
      throw new NegocioException("El curso no existe o no te pertenece.");
    return c;
  }

  @Override
  public void guardar(int id, String nombre, String descripcion, Usuario u)
      throws SQLException, NegocioException {
    profesor(u);
    nombre = nombre == null ? "" : nombre.trim();
    descripcion = descripcion == null ? "" : descripcion.trim();
    if (nombre.isEmpty() || nombre.length() > 150)
      throw new NegocioException("El nombre debe tener entre 1 y 150 caracteres.");
    if (descripcion.length() > 5000)
      throw new NegocioException("La descripción admite hasta 5000 caracteres.");
    Curso c = new Curso(id, nombre, descripcion, u.getId(), u.getNombre());
    if (id == 0) dao.crear(c);
    else if (!dao.actualizar(c))
      throw new NegocioException("El curso no existe o no te pertenece.");
  }

  @Override
  public void eliminar(int id, Usuario u) throws SQLException, NegocioException {
    profesor(u);
    if (!dao.eliminar(id, u.getId()))
      throw new NegocioException("El curso no existe o no te pertenece.");
  }
}
