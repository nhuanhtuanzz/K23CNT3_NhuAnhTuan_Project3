package prj3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NatCinemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatCinemaApplication.class, args);
        System.out.println("=========================================");
        System.out.println("✅ NAT CINEMA STARTED SUCCESSFULLY!");
        System.out.println("👉 URL: http://localhost:8080");
        System.out.println("👉 Login: http://localhost:8080/auth/login");
        System.out.println("=========================================");
	}

}
