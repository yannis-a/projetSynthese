package fr.florian.demo.service;

import fr.florian.demo.modele.Groupe;
import fr.florian.demo.repo.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeService {
    private final GroupeRepository groupeRepository;

    @Autowired
    public GroupeService(final GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    public List<Groupe> findAll() {
        return (List<Groupe>) groupeRepository.findAll();
    }

    public List<Groupe> findUserGroup(final String dn) {
        LdapQuery query = LdapQueryBuilder.query()
                                          .where("objectclass")
                                          .is("groupOfUniqueNames")
                                          .and("uniqueMember")
                                          .is(dn);
        return (List<Groupe>) groupeRepository.findAll(query);
    }

    public List<Groupe> findGroupByName(final String cn) {
        LdapQuery query = LdapQueryBuilder.query()
                                          .where("objectclass")
                                          .is("groupOfUniqueNames")
                                          .and("cn")
                                          .is(cn);
        return (List<Groupe>) groupeRepository.findAll(query);
    }
}
