package models;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Data
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "stock")
    private int stock;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
