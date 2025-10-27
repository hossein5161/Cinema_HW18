package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Role;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.IOException;

public class UserRegisterServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleString = req.getParameter("role");

        if (username == null || email == null || password == null || roleString == null) {
            resp.sendRedirect("/register");
            return;
        }

        Role role = Role.valueOf(roleString);
        User user = userService.register(username, email, password, role);

        req.setAttribute("message", role.toString());
        req.getRequestDispatcher("/register-success.jsp").forward(req, resp);
    }
}
