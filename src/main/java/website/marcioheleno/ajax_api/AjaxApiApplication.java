package website.marcioheleno.ajax_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AjaxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AjaxApiApplication.class, args);
    }

}
