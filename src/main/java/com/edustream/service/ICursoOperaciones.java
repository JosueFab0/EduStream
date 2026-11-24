package com.edustream.service;

import com.edustream.exception.NegocioException;
import com.edustream.model.*;
import java.sql.SQLException;
import java.util.List;

public interface ICursoOperaciones {
  List<Curso> listar(Usuario usuario) throws SQLException;

  Curso buscarParaEditar(int id, Usuario usuario) throws SQLException, NegocioException;

  void guardar(int id, String nombre, String descripcion, Usuario usuario)
      throws SQLException, NegocioException;

  void eliminar(int id, Usuario usuario) throws SQLException, NegocioException;
}
