package io.github.dougllasfps.quarkusSocial.domain.repository;

import io.github.dougllasfps.quarkusSocial.domain.model.Follower;
import io.github.dougllasfps.quarkusSocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped // anotação que torna a classe gerenciada pelo framework em contexto de aplicacao
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user){
        var params = Parameters
                .with("follower", follower)
                .and("user", user).map();
        PanacheQuery<Follower> query = find("follower =:follower and user =:user", params);// estamos fazendo a pesquisa pelo seguidor e usuario
        Optional<Follower> result = query.firstResultOptional();
        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId) {
        PanacheQuery<Follower> query = find("user.id", userId);
        return query.list();
    }

}
