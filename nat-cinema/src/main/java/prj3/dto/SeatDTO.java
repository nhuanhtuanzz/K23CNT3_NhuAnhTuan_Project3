package prj3.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatDTO {
    private Long id;
    private String code;      // A1, B2
    private String type;      // VIP, NORMAL
    private double price;

    // 3 trạng thái quan trọng
    private boolean isMaintenance; // Đang bảo trì (Admin khóa)
    private boolean isBooked;      // Đã có người đặt (Trong suất chiếu này)
    private boolean isSelected;    // Người dùng đang chọn (Xử lý bằng JS)
}