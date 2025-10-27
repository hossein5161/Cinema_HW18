package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Comment;
import model.Movie;
import repository.CommentRepository;
import util.Database_Connection;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {

    @Override
    public void addComment(Comment comment) {
        EntityManager em = Database_Connection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Comment> getCommentsByMovie(Movie movie) {
        EntityManager em = Database_Connection.getEntityManager();
        TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.movie = :movie", Comment.class);
        query.setParameter("movie", movie);
        List<Comment> comments = query.getResultList();
        em.close();
        return comments;
    }

    @Override
    public void deleteComment(Long commentId) {
        EntityManager em = Database_Connection.getEntityManager();
        Comment comment = em.find(Comment.class, commentId);
        if (comment != null) {
            em.getTransaction().begin();
            em.remove(comment);
            em.getTransaction().commit();
        }
        em.close();

    }
}
