package com.edustream.web;

import com.edustream.exception.NegocioException;
import com.edustream.model.Curso;
import com.edustream.service.*;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public final class CursoServlet extends HttpServlet {
  private final ICursoOperaciones servicio = new CursoService();

  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      if (r.getServletPath().equals("/app/profesor/curso")) {
        if (r.getParameter("id") != null)
          r.setAttribute("curso", servicio.buscarParaEditar(Web.id(r, "id"), Web.usuario(r)));
        r.getRequestDispatcher("/WEB-INF/views/curso-form.jsp").forward(r, s);
      } else {
        r.setAttribute("cursos", servicio.listar(Web.usuario(r)));
        r.getRequestDispatcher("/WEB-INF/views/cursos.jsp").forward(r, s);
      }
    } catch (NegocioException ex) {
      Web.volver(r, s, "/app/cursos", ex.getMessage());
    } catch (SQLException ex) {
      throw new ServletException(ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    if (!r.getServletPath().equals("/app/profesor/curso")) {
      s.sendError(405);
      return;
    }
    try {
      String accion = r.getParameter("accion");
      if ("eliminar".equals(accion)) {
        servicio.eliminar(Web.id(r, "id"), Web.usuario(r));
        Web.volver(r, s, "/app/cursos", "Curso eliminado junto con sus inscripciones.");
      } else if ("guardar".equals(accion)) {
        int id = "0".equals(r.getParameter("id")) ? 0 : Web.id(r, "id");
        servicio.guardar(
            id, r.getParameter("nombre"), r.getParameter("descripcion"), Web.usuario(r));
        Web.volver(r, s, "/app/cursos", "Curso guardado correctamente.");
      } else {
        s.sendError(400);
      }
    } catch (NegocioException ex) {
      if ("guardar".equals(r.getParameter("accion"))) {
        int id = 0;
        try {
          id = "0".equals(r.getParameter("id")) ? 0 : Web.id(r, "id");
        } catch (NegocioException ignored) {
          /* Se vuelve al formulario nuevo. */
        }
        r.setAttribute(
            "curso",
            new Curso(
                id,
                r.getParameter("nombre"),
                r.getParameter("descripcion"),
                Web.usuario(r).getId(),
                Web.usuario(r).getNombre()));
        r.setAttribute("error", ex.getMessage());
        r.getRequestDispatcher("/WEB-INF/views/curso-form.jsp").forward(r, s);
      } else Web.volver(r, s, "/app/cursos", ex.getMessage());
    } catch (SQLException ex) {
      throw new ServletException(ex);
    }
  }
}
