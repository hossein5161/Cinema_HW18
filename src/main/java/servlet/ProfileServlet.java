package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.PasswordUtil;
import java.io.IOException;
import java.io.PrintWriter;

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
        renderProfile(resp, user, null);
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
            sendMessage(resp, "Current password is incorrect!", false);
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
            sendMessage(resp, "Profile updated successfully!", true);
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(resp, "Error updating profile!", false);
        }
    }

    private void renderProfile(HttpServletResponse resp, User user, String message) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Profile</title>");
        out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>.card-custom{max-width:700px;margin:2rem auto;padding:1.5rem}</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");

        if (message != null) {
            String alertClass = message.contains("successfully") ? "alert-success" : "alert-danger";
            out.println("<div class='alert " + alertClass + "'>" + message + "</div>");
        }

        out.println("<div class='card card-custom shadow-sm'><div class='card-body'>");
        out.println("<h2 class='card-title text-center mb-4'>Profile</h2>");
        out.println("<form method='POST' action='/user/profile'>");
        out.println("<div class='mb-3 row'><label class='col-sm-3 col-form-label' for='username'>Username</label>");
        out.println("<div class='col-sm-9'><input type='text' class='form-control' id='username' name='username' value='" + user.getUsername() + "' required></div></div>");
        out.println("<div class='mb-3 row'><label class='col-sm-3 col-form-label' for='email'>Email</label>");
        out.println("<div class='col-sm-9'><input type='email' class='form-control' id='email' name='email' value='" + user.getEmail() + "' required></div></div>");
        out.println("<div class='mb-3 row'><label class='col-sm-3 col-form-label' for='newPassword'>New Password</label>");
        out.println("<div class='col-sm-9'><input type='password' class='form-control' id='newPassword' name='newPassword' placeholder='Leave empty to keep current password'></div></div>");
        out.println("<div class='mb-3 row'><label class='col-sm-3 col-form-label' for='currentPassword'>Current Password</label>");
        out.println("<div class='col-sm-9'><input type='password' class='form-control' id='currentPassword' name='currentPassword' placeholder='Required to confirm changes' required></div></div>");
        out.println("<div class='d-grid'><button type='submit' class='btn btn-primary btn-lg'>Save Changes</button></div>");
        out.println("</form></div></div></div></body></html>");
        out.close();
    }

    private void sendMessage(HttpServletResponse resp, String message, boolean success) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Profile Update</title>");
        out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'></head><body>");
        out.println("<div class='container my-5'>");
        out.println("<div class='alert " + (success ? "alert-success" : "alert-danger") + "'>" + message + "</div>");
        out.println("<a href='/login' class='btn btn-primary'>Back to Login</a>");
        out.println("</div></body></html>");
        out.close();
    }
}
