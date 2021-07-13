package web.servlet;

import service.PostService;
import storage.DbStorage.DbPostStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ApprovePostServlet", urlPatterns = "/approvePost")
public class ApprovePostServlet extends HttpServlet {

    PostService postService = new PostService(new DbPostStorage());

    public ApprovePostServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        postService.approve(id);
    }
}
