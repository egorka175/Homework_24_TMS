package web.servlet;

import service.CommentService;
import storage.DbStorage.DbCommentStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="UpdateCommentServlet",urlPatterns = "/updateComment")
public class UpdateCommentServlet extends HttpServlet {
    CommentService commentService = new CommentService(new DbCommentStorage());

    public UpdateCommentServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String newText = req.getParameter("newText");
        commentService.updateComment(id, newText);
    }
}
