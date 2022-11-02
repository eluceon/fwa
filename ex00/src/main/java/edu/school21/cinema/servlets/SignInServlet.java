package edu.school21.cinema.servlets;

import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
        userService = applicationContext.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/html/signin.html");
            PrintWriter out= resp.getWriter();
            out.println("<font color=red>Fields can't be empty</font>");
            rd.include(req, resp);
        } else {
            try {
                if (userService.signIn(email, password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("email", email);
                    //setting session to expiry in 30 mins
                    session.setMaxInactiveInterval(30 * 60);
                    Cookie userName = new Cookie("email", email);
                    resp.addCookie(userName);
                    String encodedURL = resp.encodeURL("/profile");
                    resp.sendRedirect(encodedURL);
                } else {
                    throw new RuntimeException("Sign in exception");
                }
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/signIn");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/signin.html").forward(req, resp);
    }
}
