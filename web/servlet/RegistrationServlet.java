package web.servlet;

import entity.User;
import service.UserService;
import storage.DbStorage.DbUserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/reg")
public class RegistrationServlet extends HttpServlet {
    UserService userService = new UserService(new DbUserStorage());

    public RegistrationServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role=req.getParameter("role");
        if(userService.existsByUsername(username)){
            resp.getWriter().println("There is already existing user with this name");
            return;
        }
        User user = new User(id,name,username,password, role);
        userService.save(user);
        req.getSession().setAttribute("userId", user.getId());
        resp.getWriter().println("ok");
    }
}
