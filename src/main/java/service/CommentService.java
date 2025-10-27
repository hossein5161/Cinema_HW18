package service;

import model.Comment;
import model.Movie;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getCommentsByMovie(Movie movie);
    void deleteComment(Long commentId);
}
