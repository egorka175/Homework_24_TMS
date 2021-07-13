package web.servlet;

import entity.Post;
import entity.User;
import service.PostService;
import storage.DbStorage.DbPostStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreatePostServlet", urlPatterns = "/createPost")
public class CreatePostServlet extends HttpServlet {
    PostService postservice = new PostService(new DbPostStorage());

    public CreatePostServlet() throws SQLException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        String title = req.getParameter("title");
        User author = (User) req.getSession().getAttribute("user");
        Post newPost = new Post(0,title,description,author,null,false);
        postservice.save(newPost);
    }
}
