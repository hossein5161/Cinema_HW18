package repository;

import model.Comment;
import model.Movie;

import java.util.List;

public interface CommentRepository {
    void addComment(Comment comment);
    List<Comment> getCommentsByMovie(Movie movie);
    void deleteComment(Long commentId);
}
