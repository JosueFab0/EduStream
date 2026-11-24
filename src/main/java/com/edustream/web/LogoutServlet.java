package com.edustream.web;

import java.io.IOException;
import javax.servlet.http.*;

public final class LogoutServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException {
    r.getSession().invalidate();
    s.sendRedirect(r.getContextPath() + "/login");
  }
}
