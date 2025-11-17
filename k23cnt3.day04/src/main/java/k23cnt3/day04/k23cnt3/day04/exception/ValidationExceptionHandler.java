package k23cnt3.day04.k23cnt3.day04.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        // Khởi tạo một Map để lưu trữ lỗi: Key là tên trường, Value là thông báo lỗi
        Map<String, String> errors = new HashMap<>();

        // Lấy tất cả các lỗi từ BindingResult
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            // Ép kiểu Error sang FieldError để lấy tên trường
            String fieldName = ((FieldError) error).getField();

            // Lấy thông báo lỗi mặc định
            String errorMessage = error.getDefaultMessage();

            // Đưa thông tin lỗi vào Map
            errors.put(fieldName, errorMessage);
        });

        // Trả về ResponseEntity chứa Map lỗi và mã trạng thái HTTP 400 (BAD_REQUEST)
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}