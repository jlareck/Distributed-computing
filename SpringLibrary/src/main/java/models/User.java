package models;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "id_user", sequenceName = "id_user", allocationSize = 1)
    @GeneratedValue(generator = "id_user", strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "lastname")
    private String lastname;


    @Column(name = "login")
    private String login;


    @Column(name = "password")
    private String password;


    @Column(name = "is_admin")
    private boolean isAdmin;


    @Column(name = "role")
    private String role;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
