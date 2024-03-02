package com.cubetiqs.rxapi.book;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class BookService {
    private final ConcurrentMap<String, Book> books = new ConcurrentHashMap<>();

    public BookService() {
        this.books.put("1", new Book("1", "Harry Potter", "J.K. Rowling"));
        this.books.put("2", new Book("2", "The Lord of the Rings", "J.R.R. Tolkien"));
        this.books.put("3", new Book("3", "The Hobbit", "J.R.R. Tolkien"));
    }

    public Mono<Book> findById(String id) {
        return Mono.justOrEmpty(this.books.get(id));
    }

    public Mono<Book> create(Book book) {
        this.books.put(book.id(), book);
        return Mono.just(book);
    }

    public Mono<Book> update(String id, Book book) {
        this.books.put(id, book);
        return Mono.just(book);
    }

    public Mono<Void> delete(String id) {
        this.books.remove(id);
        return Mono.empty();
    }

    public Flux<Book> findAll() {
        return Flux.fromIterable(this.books.values());
    }

    public long count() {
        return this.books.size();
    }

    public boolean existsById(String id) {
        return this.books.containsKey(id);
    }

    public void deleteAll() {
        this.books.clear();
    }

}
