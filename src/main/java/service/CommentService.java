package service;

import model.Comment;
import model.Movie;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getCommentsByMovie(Long movieId);
    void deleteComment(Long commentId);
    double getAverageRating(Long movieId);
    void updateComment(Comment comment);


}
