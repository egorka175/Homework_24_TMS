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

@WebServlet(name="UpdatePasswordServlet",urlPatterns = "/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
    UserService userService = new UserService(new DbUserStorage());

    public UpdatePasswordServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = (User) req.getSession().getAttribute("user");
        if(!oldPassword.equals(user.getPassword())){
            resp.getWriter().println("invalid password");
            return;
        }
        userService.updateUserPassword(user,newPassword);
    }
}
