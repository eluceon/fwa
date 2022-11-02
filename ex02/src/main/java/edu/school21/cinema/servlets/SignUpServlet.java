package edu.school21.cinema.servlets;

import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
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
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()
                || phoneNumber == null || phoneNumber.isEmpty() || email == null || email.isEmpty()
                || password == null || password.isEmpty()) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/html/signup.html");
            PrintWriter out= resp.getWriter();
            out.println("<font color=red>Fields can't be empty</font>");
            rd.include(req, resp);
        } else {
            try {
                if (userService.signUp(firstName, lastName, phoneNumber, email, password, req.getRemoteAddr())) {
                    resp.sendRedirect(req.getContextPath() + "/signIn");
                } else {
                    throw new RuntimeException("Sign up exception");
                }
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/signUp");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/signup.html").forward(req, resp);
    }
}
