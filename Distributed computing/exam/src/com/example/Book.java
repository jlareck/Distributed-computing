package com.example;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    public int id;
    public String name;
    public List<String> authors;
    public String publisher;
    public int year;
    public int numberOfPages;
    public int price;

    public Book(int id, String name, List<String> authors, String publisher, int year, int numberOfPages, int price) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.price = price;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", numberOfPages=" + numberOfPages +
                ", price=" + price +
                '}';
    }
}
