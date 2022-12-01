package io.github.dougllasfps.quarkusSocial.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // tipo correspondente ao bigserial utilizado no banco de dados

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

}
