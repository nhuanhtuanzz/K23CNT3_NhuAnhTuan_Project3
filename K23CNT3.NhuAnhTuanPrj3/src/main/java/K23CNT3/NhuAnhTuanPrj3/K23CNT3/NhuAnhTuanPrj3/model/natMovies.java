package K23CNT3.NhuAnhTuanPrj3.K23CNT3.NhuAnhTuanPrj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class natMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natMovieId;

    private String natTitle;

    @Column(columnDefinition = "TEXT")
    private String natDescription;

    private Integer natDuration;

    private java.sql.Date natReleaseDate;

    private String natPosterUrl;
}
