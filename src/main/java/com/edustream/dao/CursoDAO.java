package com.edustream.dao;

import com.edustream.config.Conexion;
import com.edustream.model.Curso;
import java.sql.*;
import java.util.*;

public final class CursoDAO {
  private Curso map(ResultSet r) throws SQLException {
    return new Curso(
        r.getInt("id_curso"),
        r.getString("nombre_curso"),
        r.getString("descripcion"),
        r.getInt("id_profesor"),
        r.getString("profesor"));
  }

  public List<Curso> listar(Integer profesor) throws SQLException {
    List<Curso> cursos = new ArrayList<>();
    String sql =
        "SELECT c.*,u.nombre profesor FROM cursos c JOIN usuarios u ON u.id_usuario=c.id_profesor"
            + (profesor == null ? "" : " WHERE c.id_profesor=?")
            + " ORDER BY c.id_curso DESC";
    try (Connection c = Conexion.getConexion();
        PreparedStatement p = c.prepareStatement(sql)) {
      if (profesor != null) p.setInt(1, profesor);
      try (ResultSet r = p.executeQuery()) {
        while (r.next()) cursos.add(map(r));
      }
    }
    return cursos;
  }

  public Curso buscar(int id) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement(
                "SELECT c.*,u.nombre profesor FROM cursos c JOIN usuarios u ON"
                    + " u.id_usuario=c.id_profesor WHERE c.id_curso=?")) {
      p.setInt(1, id);
      try (ResultSet r = p.executeQuery()) {
        return r.next() ? map(r) : null;
      }
    }
  }

  public int crear(Curso curso) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement(
                "INSERT INTO cursos(nombre_curso,descripcion,id_profesor) VALUES(?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
      p.setString(1, curso.getNombre());
      p.setString(2, curso.getDescripcion());
      p.setInt(3, curso.getIdProfesor());
      p.executeUpdate();
      try (ResultSet r = p.getGeneratedKeys()) {
        if (r.next()) return r.getInt(1);
        throw new SQLException("No se obtuvo el curso creado.");
      }
    }
  }

  public boolean actualizar(Curso curso) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement(
                "UPDATE cursos SET nombre_curso=?,descripcion=? WHERE id_curso=? AND"
                    + " id_profesor=?")) {
      p.setString(1, curso.getNombre());
      p.setString(2, curso.getDescripcion());
      p.setInt(3, curso.getId());
      p.setInt(4, curso.getIdProfesor());
      return p.executeUpdate() == 1;
    }
  }

  public boolean eliminar(int id, int profesor) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p =
            c.prepareStatement("DELETE FROM cursos WHERE id_curso=? AND id_profesor=?")) {
      p.setInt(1, id);
      p.setInt(2, profesor);
      return p.executeUpdate() == 1;
    }
  }
}
