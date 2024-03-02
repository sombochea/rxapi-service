package com.cubetiqs.rxapi;

import com.cubetiqs.rxapi.book.BookRouter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class RxApiApplication {
    private final BookRouter bookRouter;
    public RxApiApplication(BookRouter bookRouter) {
        this.bookRouter = bookRouter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RxApiApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route().GET("/", req -> ServerResponse.ok().bodyValue("ok"))
                .add(bookRouter.routes())
                .build();
    }
}
