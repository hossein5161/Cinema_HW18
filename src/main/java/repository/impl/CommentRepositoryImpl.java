package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Comment;
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
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Comment> getCommentsByMovie(Long movieId) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            TypedQuery<Comment> q = em.createQuery(
                    "select c from Comment c " +
                            "join fetch c.user u " +
                            "where c.movie.id = :mid " +
                            "order by c.createdAt DESC", Comment.class
            );
            q.setParameter("mid", movieId);
            return q.getResultList();
        }
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

    @Override
    public double getAverageRating(Long movieId) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            Double avg = em.createQuery(
                            "select coalesce(AVG(c.rating), 0) from Comment c where c.movie.id = :mid",
                            Double.class
                    ).setParameter("mid", movieId)
                    .getSingleResult();
            return avg != null ? avg : 0.0;
        }
    }

    @Override
    public void updateComment(Comment comment) {
        EntityManager em = Database_Connection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
