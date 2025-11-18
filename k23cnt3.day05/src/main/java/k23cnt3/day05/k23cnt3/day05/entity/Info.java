package k23cnt3.day05.k23cnt3.day05.entity;

public class Info {

    private String name;
    private String nickname;
    private String email;
    private String website;

    // 1. DEFAULT CONSTRUCTOR (Cần thiết cho Spring/JPA/Jackson)
    public Info() {
        // Hàm khởi tạo không tham số (mặc định)
    }

    // 2. PARAMETERIZED CONSTRUCTOR (Cần thiết để khởi tạo đối tượng trong Controller)
    // Thứ tự tham số PHẢI khớp với thứ tự gọi trong Controller
    public Info(String name, String nickname, String email, String website) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.website = website;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}