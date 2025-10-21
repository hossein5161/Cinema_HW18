package model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "watchlists",
                    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","movie_id"}))
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;

    public Watchlist(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }
}
