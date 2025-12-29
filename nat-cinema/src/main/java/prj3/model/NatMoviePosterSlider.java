package prj3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MoviePosterSlider")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NatMoviePosterSlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long natPosterId;

    // Liên kết với phim (Có thể null nếu đây là banner quảng cáo chung)
    @ManyToOne
    @JoinColumn(name = "natMovieId", nullable = true)
    private NatMovies natMovie;

    // Ảnh hiển thị (Bắt buộc)
    private String natImageUrl;

    // Thứ tự hiển thị
    private Integer natSortOrder = 0;

    // --- CÁC FIELD MỚI THÊM ĐỂ KHỚP VỚI HTML ---

    // Tiêu đề riêng cho Banner (VD: "Phim Hot Tháng Này")
    private String natTitle;

    // Mô tả ngắn dưới tiêu đề
    private String natCaption;

    // Link tùy chỉnh (nếu muốn dẫn sang trang khuyến mãi thay vì trang phim)
    private String natButtonLink;

    // Chữ hiển thị trên nút (VD: "Đặt vé ngay", "Xem chi tiết")
    private String natButtonText;
}