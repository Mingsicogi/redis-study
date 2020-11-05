package my.study.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

    /**
     * lettuce configuration each data structure
     *
     * @author minssogi
     */
    @Component
    public static class LettuceConfiguration {

        private final RedisClient redisClient;

        public LettuceConfiguration(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") Integer redisPort,
                                    @Value("${spring.redis.database}") Integer redisDbNo) {
            redisClient = RedisClient.create(RedisURI.builder().withHost(redisHost).withPort(redisPort).withDatabase(redisDbNo).build());
        }

        @Bean
        public RedisCommands<String, String> stringRedisCommands() {
            return redisClient.connect().sync();
        }
    }

    /**
     * Global Exception handler
     *
     * @author minssogi
     */
    @ControllerAdvice
    public static class CommonException {

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<Object> NotFoundException() {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<Object> BadRequestException() {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public static class NotFoundException extends RuntimeException { }
    public static class BadRequestException extends RuntimeException { }
}
