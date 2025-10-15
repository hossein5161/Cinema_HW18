package servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.HtmlUtil;

import java.io.IOException;


public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String htmlForm = "<form method=POST action=users>"
                + "Username: <input type=text name=username required/><br/>"
                + "Email: <input type=email name=email required/><br/>"
                + "Password: <input type=password name=password required/><br/>"
                + "<input type=submit value='Add User'/>"
                + "</form>";
        HtmlUtil.writeHtml(resp, "Add User", htmlForm);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.registerUser(user);
        HtmlUtil.writeHtml(resp,"Registration","<h2>User registered successfully!</h2>");
    }
}
