package mat.sri3jms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Sri3JmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sri3JmsApplication.class, args);
    }

}
