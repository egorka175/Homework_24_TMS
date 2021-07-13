package storage.storageInterfaces;

import entity.Comment;
import entity.Post;

import java.util.List;

public interface CommentStorage {
    void save(Comment comment, int postId);
    String delete(int id);
    void upgradeComment(int id, String newCommentText);
    List<String> getComments(String userNameAuthor);
    Comment getComment(int id);
    void approve(int id);
    void disapprove(int id);
}
