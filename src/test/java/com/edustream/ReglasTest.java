package com.edustream;

import static org.junit.jupiter.api.Assertions.*;

import com.edustream.exception.*;
import com.edustream.model.*;
import com.edustream.security.Passwords;
import com.edustream.service.*;
import org.junit.jupiter.api.Test;

class ReglasTest {
  @Test
  void contrasenasNoAceptanValoresIncorrectos() {
    String h = Passwords.hash("123456");
    assertTrue(Passwords.verify("123456", h));
    assertFalse(Passwords.verify("otra", h));
    assertFalse(Passwords.verify("123456", "123456"));
    assertFalse(Passwords.verify("123456", "pbkdf2$999999999$x$x"));
    assertNotEquals(h, Passwords.hash("123456"));
  }

  @Test
  void estudianteNoPuedeGestionarCursos() {
    Usuario e = new Estudiante(2, "Juan", "juan@correo.com");
    assertThrows(NegocioException.class, () -> new CursoService().guardar(0, "Curso", "", e));
    assertThrows(NegocioException.class, () -> new CursoService().eliminar(1, e));
  }

  @Test
  void profesorNoPuedeInscribirse() {
    assertThrows(
        NegocioException.class,
        () -> new InscripcionService().inscribir(1, new Profesor(1, "Profe", "p@correo.com")));
  }

  @Test
  void validaCursoAntesDeEscribir() {
    CursoService s = new CursoService();
    Usuario p = new Profesor(1, "Profe", "p@correo.com");
    assertThrows(NegocioException.class, () -> s.guardar(0, " ", "", p));
    assertThrows(NegocioException.class, () -> s.guardar(0, "x".repeat(151), "", p));
    assertThrows(NegocioException.class, () -> s.guardar(0, "Curso", "x".repeat(5001), p));
  }
}
