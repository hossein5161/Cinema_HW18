package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.PasswordUtil;

import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        req.setAttribute("username", user.getUsername());
        req.setAttribute("email", user.getEmail());
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");

        if (!PasswordUtil.verify(currentPassword, user.getPasswordHash())) {
            resp.sendRedirect("/user/profile?message=Current password is incorrect!");
            return;
        }

        user.setUsername(username);
        user.setEmail(email);
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPasswordHash(PasswordUtil.encode(newPassword));
        }

        try {
            userService.update(user);
            session.setAttribute("user", user);
            resp.sendRedirect("/user/profile?message=Profile updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/user/profile?message=Error updating profile!");
        }
    }
}
