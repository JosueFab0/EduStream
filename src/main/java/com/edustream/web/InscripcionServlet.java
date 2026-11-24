package com.edustream.web;

import com.edustream.dao.InscripcionDAO;
import com.edustream.exception.NegocioException;
import com.edustream.service.InscripcionService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public final class InscripcionServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      r.setAttribute("inscripciones", new InscripcionDAO().listar(Web.usuario(r)));
      r.getRequestDispatcher("/WEB-INF/views/inscripciones.jsp").forward(r, s);
    } catch (SQLException ex) {
      throw new ServletException(ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      InscripcionService service = new InscripcionService();
      String a = r.getParameter("accion");
      if ("inscribir".equals(a)) {
        service.inscribir(Web.id(r, "curso"), Web.usuario(r));
        Web.volver(r, s, "/app/inscripciones", "Inscripción realizada.");
      } else if ("cancelar".equals(a)) {
        service.cancelar(Web.id(r, "id"), Web.usuario(r));
        Web.volver(r, s, "/app/inscripciones", "Inscripción cancelada.");
      } else {
        s.sendError(400);
      }
    } catch (NegocioException ex) {
      Web.volver(r, s, "/app/inscripciones", ex.getMessage());
    } catch (SQLException ex) {
      if (ex.getErrorCode() == 1452) {
        Web.volver(r, s, "/app/cursos", "El curso ya no está disponible.");
      } else throw new ServletException(ex);
    }
  }
}
