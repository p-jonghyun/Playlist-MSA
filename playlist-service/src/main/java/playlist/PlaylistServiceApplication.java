package playlist;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlaylistServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaylistServiceApplication.class, args);
    }

}