package service;

import entity.Comment;
import entity.Post;
import storage.storageInterfaces.CommentStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentService {
    private final CommentStorage storage;

    public CommentService(CommentStorage storage) {
        this.storage = storage;
    }

    public void addComment(Comment comment, int postId){
        storage.save(comment,postId);
    }



    public void deleteComment(int commentId){
        storage.delete(commentId);
    }


    public List<String> getAllComments(String userNameAuthor, boolean approved){
        return storage.getComments(userNameAuthor,approved);
    }

    public List<Comment> getApprovedComments(String userNameAuthor){
        return storage.getComments(userNameAuthor).stream().filter(Comment::isApproved).collect(Collectors.toList());
    }

    public List<Comment> getApprovedComments(int postId){
        Post post = new Post(postId,null,null,null,null);
        return storage.getComments(post).stream().filter(Comment::isApproved).collect(Collectors.toList());
    }

    public List<Comment> getDisapprovedComments(Post post){
        return storage.getComments(post).stream().filter(com -> !com.isApproved()).collect(Collectors.toList());
    }

    public List<Comment> getDisapprovedComments(int postId){
        Post post = new Post(postId,null,null,null,null);
        return storage.getComments(post).stream().filter(com -> !com.isApproved()).collect(Collectors.toList());
    }

    public void updateComment(int id, String newCommentText){
        storage.upgradeComment(id,newCommentText);
    }

    public Optional<Comment> getComment(int id){
        return Optional.ofNullable(storage.getComment(id));
    }

    public void approve(Comment comment){
        storage.approve(comment);
    }

    public void approve(int commentId){
        storage.approve(new Comment(commentId,null,null));
    }
}
