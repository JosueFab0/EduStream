package com.edustream.web;

import com.edustream.dao.UsuarioDAO;
import com.edustream.model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public final class LoginServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    if (r.getSession().getAttribute("usuarioLogueado") != null) {
      s.sendRedirect(r.getContextPath() + "/app/inicio");
      return;
    }
    r.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(r, s);
  }

  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s)
      throws ServletException, IOException {
    String email = r.getParameter("email"), password = r.getParameter("password");
    if (email == null || email.length() > 100 || password == null || password.length() > 128) {
      r.setAttribute("error", "Revisa el correo y la contraseña.");
      doGet(r, s);
      return;
    }
    try {
      Usuario u = new UsuarioDAO().validarLogin(email.trim(), password);
      if (u == null) {
        r.setAttribute("error", "Correo o contraseña incorrectos.");
        doGet(r, s);
        return;
      }
      r.changeSessionId();
      r.getSession().setAttribute("usuarioLogueado", u);
      r.getSession().setAttribute("csrf", java.util.UUID.randomUUID().toString());
      s.sendRedirect(r.getContextPath() + "/app/inicio");
    } catch (SQLException ex) {
      throw new ServletException("No se pudo consultar la base de datos.", ex);
    }
  }
}
