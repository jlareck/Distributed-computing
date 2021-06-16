package models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Data
@NoArgsConstructor
public class Reading {

    @GeneratedValue(generator = "id_reading", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_reading", sequenceName = "id_reading", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;


    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
