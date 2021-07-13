package storage.DbStorage;

import entity.Comment;
import entity.Post;
import entity.User;
import storage.storageInterfaces.CommentStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbCommentStorage extends DbStorage implements CommentStorage {

    DbUserStorage dbUserStorage = new DbUserStorage();


    public static void main(String[] args)  {
        DbCommentStorage db=new DbCommentStorage();
        db.save(new Comment(1,new User(1,"egor","ewfdwe","efdwjebwej","USER",true),"efrtdadfcdfwecwecf/welckw'cpkwecpwec",true),2);

    }

    @Override
    public void save(Comment comment, int postId) {
        if(comment==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into comments values(DEFAULT,?,?,?,?) returning id");
            preparedStatement.setString(1,comment.getText());
            preparedStatement.setInt(2,postId);
            preparedStatement.setInt(3,comment.getUser().getId());
            preparedStatement.setBoolean(4,comment.isApproved());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int commentId = resultSet.getInt(1);
            preparedStatement.execute();
            comment.setId(commentId);
        } catch (SQLException throwables) {
                throwables.printStackTrace();
        }
    }

    @Override
    public String delete(int id) {
       String rez=null;
        if (id==0) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from comments where id=?");
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        rez="Пользователь удален";
        }else {
            rez="Пользователь не удален";
        }
        return rez;
    }

    @Override
    public void upgradeComment(int id, String newCommentText) {
        if(id==0||newCommentText==null) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set text = ? where id=?");
            preparedStatement.setString(1,newCommentText);
            preparedStatement.setInt(2,id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<String> getComments(String userNameAuthor, boolean approved) {
        List<String> comments = new ArrayList<>();
        if (userNameAuthor != null) {

            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select text from comments");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String text = resultSet.getString(1);
                    String rez = userNameAuthor + ":" + "\n" + text+"\n"+"("+approved+")";
                    comments.add(rez);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return comments;
    }

            @Override
    public Comment getComment(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from comments where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            int commentId = resultSet.getInt(1);
            String text = resultSet.getString(2);
            int postId = resultSet.getInt(3);
            int userId = resultSet.getInt(4);
            boolean approved=resultSet.getBoolean(5);
            User user = dbUserStorage.getUserById(userId);
            return new Comment(commentId, user, text, approved);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void approve(int id) {
        if(id==0) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set approved=true where id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void disapprove(int id) {
        if(id==0) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update comments set approved=false where id=? ");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
