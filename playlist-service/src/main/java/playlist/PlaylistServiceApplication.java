package playlist;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude =  org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@EnableJpaAuditing
public class PlaylistServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaylistServiceApplication.class, args);
    }

}