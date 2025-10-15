package model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name="user_movie_watchlist"
            ,joinColumns = @JoinColumn(name="user_id")
            ,inverseJoinColumns = @JoinColumn(name="movie_id"))
    private Set<Movie> watchlist = new HashSet<>();
}
