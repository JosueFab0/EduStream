package com.edustream.web;

import com.edustream.model.Usuario;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.*;
import javax.servlet.http.*;

public final class SeguridadFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) request;
    HttpServletResponse s = (HttpServletResponse) response;
    r.setCharacterEncoding("UTF-8");
    s.setCharacterEncoding("UTF-8");
    s.setHeader("X-Content-Type-Options", "nosniff");
    s.setHeader("X-Frame-Options", "DENY");
    s.setHeader(
        "Content-Security-Policy",
        "default-src 'self'; style-src 'self'; form-action 'self'; frame-ancestors 'none'; base-uri"
            + " 'self'");
    String path = r.getServletPath();
    if (path.startsWith("/assets/")) {
      chain.doFilter(r, s);
      return;
    }
    s.setHeader("Cache-Control", "no-store");
    HttpSession ses = r.getSession();
    if (ses.getAttribute("csrf") == null) ses.setAttribute("csrf", UUID.randomUUID().toString());
    boolean protegida = path.startsWith("/app/");
    Usuario u = (Usuario) ses.getAttribute("usuarioLogueado");
    if (protegida && u == null) {
      s.sendRedirect(r.getContextPath() + "/login");
      return;
    }
    if (path.startsWith("/app/profesor/") && !u.puedeGestionarCursos()) {
      s.sendError(403);
      return;
    }
    if ("POST".equals(r.getMethod()) && !ses.getAttribute("csrf").equals(r.getParameter("csrf"))) {
      s.sendError(403);
      return;
    }
    try {
      chain.doFilter(r, s);
    } catch (ServletException | RuntimeException ex) {
      r.getServletContext().log("Error al procesar la solicitud", ex);
      throw ex;
    }
  }
}
