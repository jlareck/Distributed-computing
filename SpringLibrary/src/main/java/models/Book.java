package models;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "id_books", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_books", sequenceName = "id_books", allocationSize = 1)
    private long id;
    @Basic
    @Column(name = "title")
    private String title;

    @Basic
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
