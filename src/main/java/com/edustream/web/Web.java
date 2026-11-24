package com.edustream.web;

import com.edustream.exception.NegocioException;
import com.edustream.model.Usuario;
import java.io.IOException;
import javax.servlet.http.*;

public final class Web {
  private Web() {}

  public static Usuario usuario(HttpServletRequest r) {
    return (Usuario) r.getSession().getAttribute("usuarioLogueado");
  }

  public static int id(HttpServletRequest r, String key) throws NegocioException {
    try {
      int id = Integer.parseInt(r.getParameter(key));
      if (id <= 0) throw new NumberFormatException();
      return id;
    } catch (NumberFormatException ex) {
      throw new NegocioException("El identificador recibido no es válido.");
    }
  }

  public static void volver(
      HttpServletRequest r, HttpServletResponse s, String ruta, String mensaje) throws IOException {
    r.getSession().setAttribute("mensaje", mensaje);
    s.sendRedirect(r.getContextPath() + ruta);
  }
}
