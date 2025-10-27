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
    public List<Comment> getCommentsByMovie(Movie movie) {
        return commentRepository.getCommentsByMovie(movie);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }
}
