package io.github.dougllasfps.quarkusSocial.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_text")
    private String text;
    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id") // usar quando for chave estrangeira
    private User user;

    @PrePersist // essa anotação é processada antes do objeto ser gravado no banco de dados
    public void prePersist() {
        setDateTime(LocalDateTime.now());
    }
}
