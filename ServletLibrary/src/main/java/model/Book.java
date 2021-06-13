package model;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String title;
    private int stock;
}
