package prj3.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name = "Movies")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatMovies {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natMovieId;

    private String natTitle;

    @Column(columnDefinition = "TEXT")
    private String natDescription;

    private Integer natDuration;
    private LocalDate natReleaseDate;
    private String natPosterUrl;
}