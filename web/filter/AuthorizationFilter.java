package web.filter;

import service.UserService;
import storage.DbStorage.DbUserStorage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(servletNames = "AuthorizationServlet")
public class AuthorizationFilter extends HttpFilter {
UserService userService = new UserService(new DbUserStorage());

    public AuthorizationFilter() throws SQLException {
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(req.getSession().getAttribute("user")!=null){
            res.getWriter().println("сначала нужно выйти)))");
            return;
        }
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        if(password==null || username==null || password.isBlank() || username.isBlank()) return;
        chain.doFilter(req,res);
    }
}
