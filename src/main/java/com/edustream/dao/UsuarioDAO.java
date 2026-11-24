package com.edustream.dao;

import com.edustream.config.Conexion;
import com.edustream.model.*;
import com.edustream.security.Passwords;
import java.sql.*;

public final class UsuarioDAO {
  public Usuario validarLogin(String email, String password) throws SQLException {
    try (Connection c = Conexion.getConexion();
        PreparedStatement p = c.prepareStatement("SELECT * FROM usuarios WHERE email=?")) {
      p.setString(1, email);
      try (ResultSet r = p.executeQuery()) {
        if (!r.next() || !Passwords.verify(password, r.getString("password"))) return null;
        return "PROFESOR".equals(r.getString("rol"))
            ? new Profesor(r.getInt("id_usuario"), r.getString("nombre"), r.getString("email"))
            : new Estudiante(r.getInt("id_usuario"), r.getString("nombre"), r.getString("email"));
      }
    }
  }
}
