package com.edustream.web;

import com.edustream.dao.InscripcionDAO;
import com.edustream.service.CursoService;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public final class InicioServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    try {
      r.setAttribute("totalCursos", new CursoService().listar(Web.usuario(r)).size());
      r.setAttribute("totalInscripciones", new InscripcionDAO().listar(Web.usuario(r)).size());
      r.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(r, s);
    } catch (SQLException ex) {
      throw new ServletException(ex);
    }
  }
}
