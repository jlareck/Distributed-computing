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
@Table(name = "book_request")
public class BookRequest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "user_id")
    private long userId;

    @Basic
    @Column(name ="book_id")
    private long bookId;
    @Basic
    @Column(name = "accepted")
    private boolean isAccepted;


    public BookRequest(long userId, long bookId, boolean accepted) {
        userId = userId;
        bookId = bookId;
        isAccepted = accepted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
