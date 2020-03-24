package brk.simulator;

import brk.simulator.utils.JAXBUtils;
import generated.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Starting Mitug Simulator ===");
        SpringApplication.run(Main.class);
    }
}
 