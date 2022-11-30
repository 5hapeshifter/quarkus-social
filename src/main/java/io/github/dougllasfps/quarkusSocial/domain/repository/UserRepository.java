package io.github.dougllasfps.quarkusSocial.domain.repository;

import io.github.dougllasfps.quarkusSocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
