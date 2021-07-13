package web.servlet;

import entity.Comment;
import entity.User;
import service.CommentService;
import storage.DbStorage.DbCommentStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreateCommentServlet", urlPatterns = "/createComment")
public class CreateCommentServlet extends HttpServlet {
    CommentService commentService = new CommentService(new DbCommentStorage());

    public CreateCommentServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postid = Integer.parseInt(req.getParameter("id"));
        User user = (User) req.getSession().getAttribute("user");
        String text = req.getParameter("text");
        Comment comment = new Comment(0, user, text);
        commentService.addComment(comment, postid);
    }
}
