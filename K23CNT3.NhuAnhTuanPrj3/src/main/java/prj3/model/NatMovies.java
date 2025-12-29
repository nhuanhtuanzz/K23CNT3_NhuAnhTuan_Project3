package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Movies")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natMovieId;
    private String natTitle;
    private String natDescription;
    private Integer natDuration;
    private java.sql.Date natReleaseDate;
    private String natPosterUrl;
}
