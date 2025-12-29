package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MoviePosterSlider")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class NatMoviePosterSlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natPosterId;

    private Long natMovieId;
    private String natImageUrl;
    private Integer natSortOrder;
}
