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
    @GeneratedValue(generator = "id_requests", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_requests", sequenceName = "id_requests", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

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
