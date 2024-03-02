package com.cubetiqs.rxapi.book;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class BookRouter {
    private final BookService bookService;

    public BookRouter(BookService bookService) {
        this.bookService = bookService;
    }

    public RouterFunction<ServerResponse> routes() {
        return route().GET("/books", req -> ServerResponse.ok().body(bookService.findAll(), Book.class))
                .GET("/books/{id}", req -> ServerResponse.ok().body(bookService.findById(req.pathVariable("id")), Book.class))
                .POST("/books", req -> req.bodyToMono(Book.class).flatMap(bookService::create)
                        .flatMap(book -> ServerResponse.created(URI.create("/books/" + book.id())).bodyValue(book)))
                .PUT("/books/{id}", req -> req.bodyToMono(Book.class).flatMap(book -> bookService.update(req.pathVariable("id"), book))
                        .flatMap(book -> ServerResponse.ok().bodyValue(book)))
                .DELETE("/books/{id}", req -> bookService.delete(req.pathVariable("id")).then(ServerResponse.noContent().build()))
                .build();
    }
}