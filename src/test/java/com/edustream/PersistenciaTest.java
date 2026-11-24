package com.edustream;

import static org.junit.jupiter.api.Assertions.*;

import com.edustream.config.Conexion;
import com.edustream.dao.*;
import com.edustream.exception.*;
import com.edustream.model.*;
import com.edustream.security.Passwords;
import com.edustream.service.*;
import java.sql.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "EDUSTREAM_INTEGRATION", matches = "true")
class PersistenciaTest {
  private int usuario(Connection c, String nombre, String rol) throws SQLException {
    try (PreparedStatement p =
        c.prepareStatement(
            "INSERT INTO usuarios(nombre,email,password,rol) VALUES(?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS)) {
      p.setString(1, nombre);
      p.setString(2, nombre + "@test.local");
      p.setString(3, Passwords.hash("Prueba123!"));
      p.setString(4, rol);
      p.executeUpdate();
      try (ResultSet r = p.getGeneratedKeys()) {
        r.next();
        return r.getInt(1);
      }
    }
  }

  @Test
  void mysqlCrudRolesDuplicadosYCascada() throws Exception {
    try (Connection c = Conexion.getConexion()) {
      assertTrue(
          c.getCatalog().endsWith("_test"),
          "Usar exclusivamente una base cuyo nombre termine en _test.");
      String suffix = java.util.UUID.randomUUID().toString();
      int p1 = 0, p2 = 0, e = 0;
      try {
        p1 = usuario(c, "prof1" + suffix, "PROFESOR");
        p2 = usuario(c, "prof2" + suffix, "PROFESOR");
        e = usuario(c, "est" + suffix, "ESTUDIANTE");
        Usuario profesor = new Profesor(p1, "Profe", "p@test.local"),
            otro = new Profesor(p2, "Otro", "o@test.local"),
            estudiante = new Estudiante(e, "Est", "e@test.local");
        UsuarioDAO ud = new UsuarioDAO();
        assertInstanceOf(
            Profesor.class, ud.validarLogin("prof1" + suffix + "@test.local", "Prueba123!"));
        assertInstanceOf(
            Estudiante.class, ud.validarLogin("est" + suffix + "@test.local", "Prueba123!"));
        assertNull(ud.validarLogin("prof1" + suffix + "@test.local", "incorrecta"));
        assertNull(ud.validarLogin("' OR 1=1 --", "x"));
        CursoService cs = new CursoService();
        cs.guardar(0, "Curso prueba", "Inicial", profesor);
        CursoDAO cd = new CursoDAO();
        int id = cd.listar(p1).get(0).getId();
        assertThrows(NegocioException.class, () -> cs.buscarParaEditar(id, otro));
        assertThrows(NegocioException.class, () -> cs.guardar(id, "Ajeno", "", otro));
        assertThrows(NegocioException.class, () -> cs.eliminar(id, otro));
        assertTrue(cd.listar(p2).isEmpty());
        cs.guardar(id, "Curso actualizado", "Descripción", profesor);
        assertEquals("Curso actualizado", cd.buscar(id).getNombre());
        InscripcionService is = new InscripcionService();
        is.inscribir(id, estudiante);
        assertThrows(InscripcionDuplicadaException.class, () -> is.inscribir(id, estudiante));
        InscripcionDAO ins = new InscripcionDAO();
        assertEquals(1, ins.listar(profesor).size());
        assertTrue(ins.listar(otro).isEmpty());
        int iid = ins.listar(estudiante).get(0).getId();
        assertFalse(ins.cancelar(iid, p2));
        is.cancelar(iid, estudiante);
        assertTrue(ins.listar(estudiante).isEmpty());
        is.inscribir(id, estudiante);
        cs.eliminar(id, profesor);
        assertNull(cd.buscar(id));
        assertTrue(ins.listar(estudiante).isEmpty());
      } finally {
        try (PreparedStatement p =
            c.prepareStatement("DELETE FROM usuarios WHERE id_usuario IN(?,?,?)")) {
          p.setInt(1, p1);
          p.setInt(2, p2);
          p.setInt(3, e);
          p.executeUpdate();
        }
      }
    }
  }
}
