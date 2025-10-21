package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Role;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;


public class UserLoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

       User user = userService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30*60);
            if (user.getRole() == Role.ADMIN) {
                resp.sendRedirect("/admin/dashboard");
            } else {
                resp.sendRedirect("/user/dashboard");
            }
            return;
        }
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body>");
        writer.println("<h3>Invalid credentials.Please try again</h3>");
        writer.println("<a href=/login>Back to Login</a>");
        writer.println("</body></html>");

    }
}