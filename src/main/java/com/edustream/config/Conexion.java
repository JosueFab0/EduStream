package com.edustream.config;

import java.sql.*;

public final class Conexion {
  private Conexion() {}

  private static String env(String key, String fallback) {
    String v = System.getenv(key);
    return v == null ? fallback : v;
  }

  public static Connection getConexion() throws SQLException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException ex) {
      throw new SQLException("Falta el conector de MySQL.", ex);
    }
    return DriverManager.getConnection(
        env(
            "EDUSTREAM_DB_URL",
            "jdbc:mysql://localhost:3306/edustream_db?connectionTimeZone=America/Costa_Rica"),
        env("EDUSTREAM_DB_USER", "edustream_app"),
        env("EDUSTREAM_DB_PASSWORD", ""));
  }
}
