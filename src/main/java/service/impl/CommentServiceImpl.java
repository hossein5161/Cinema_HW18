package service.impl;

import model.Comment;
import model.Movie;
import repository.CommentRepository;
import repository.impl.CommentRepositoryImpl;
import service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository = new CommentRepositoryImpl();

    @Override
    public void addComment(Comment comment) {
        commentRepository.addComment(comment);
    }

    @Override
    public List<Comment> getCommentsByMovie(Long movieId) {
        return commentRepository.getCommentsByMovie(movieId);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }

    @Override
    public double getAverageRating(Long movieId) {
        return commentRepository.getAverageRating(movieId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.updateComment(comment);
    }
}
