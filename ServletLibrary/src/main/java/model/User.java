package model;

import lombok.Data;

@Data
public class User {
    private long id;
    private String name;
    private String lastname;
    private String login;
    private String password;
    private boolean isAdmin;
}
