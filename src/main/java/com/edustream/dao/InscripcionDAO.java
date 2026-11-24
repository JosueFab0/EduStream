package com.edustream.dao;

import com.edustream.config.Conexion;
import com.edustream.exception.*;
import com.edustream.model.*;
import java.sql.*;
import java.util.*;

public final class InscripcionDAO {
  public void crear(int estudiante, int curso) throws SQLException, InscripcionDuplicadaException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement(
                "INSERT INTO inscripciones(id_estudiante,id_curso,fecha_inscripcion)"
                    + " VALUES(?,?,CURRENT_DATE)")) {
      p.setInt(1, estudiante);
      p.setInt(2, curso);
      p.executeUpdate();
    } catch (SQLException ex) {
      if (ex.getErrorCode() == 1062) throw new InscripcionDuplicadaException();
      throw ex;
    }
  }

  public boolean cancelar(int id, int estudiante) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement(
                "DELETE FROM inscripciones WHERE id_inscripcion=? AND id_estudiante=?")) {
      p.setInt(1, id);
      p.setInt(2, estudiante);
      return p.executeUpdate() == 1;
    }
  }

  public List<Inscripcion> listar(Usuario usuario) throws SQLException {
    List<Inscripcion> lista = new ArrayList<>();
    String sql =
        "SELECT i.*,c.nombre_curso,u.nombre estudiante FROM inscripciones i JOIN cursos c ON"
            + " c.id_curso=i.id_curso JOIN usuarios u ON u.id_usuario=i.id_estudiante WHERE "
            + (usuario.puedeGestionarCursos() ? "c.id_profesor=?" : "i.id_estudiante=?")
            + " ORDER BY i.id_inscripcion DESC";
    try (Connection c = Conexion.getConexion();
        PreparedStatement p = c.prepareStatement(sql)) {
      p.setInt(1, usuario.getId());
      try (ResultSet r = p.executeQuery()) {
        while (r.next())
          lista.add(
              new Inscripcion(
                  r.getInt("id_inscripcion"),
                  r.getInt("id_curso"),
                  r.getString("nombre_curso"),
                  r.getString("estudiante"),
                  r.getDate("fecha_inscripcion").toLocalDate()));
      }
    }
    return lista;
  }
}
