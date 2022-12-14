package io.github.dougllasfps.quarkusSocial.domain.repository;

import io.github.dougllasfps.quarkusSocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped // anotação que torna a classe gerenciada pelo framework em contexto de aplicacao
public class UserRepository implements PanacheRepository<User> {

}
