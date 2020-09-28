package fr.florian.demo.repo;

import fr.florian.demo.modele.User;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<User> {
}
