package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
public class Reading {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "book_id")
    private long bookId;

    @Basic
    @Column(name = "user_id")
    private long userId;


    public Reading(long userId, long bookId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
