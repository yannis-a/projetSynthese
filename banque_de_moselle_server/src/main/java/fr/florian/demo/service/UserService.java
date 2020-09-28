package fr.florian.demo.service;

import fr.florian.demo.modele.Groupe;
import fr.florian.demo.modele.User;
import fr.florian.demo.repo.UserRepository;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GroupeService groupeService;
    private final LdapTemplate ldapTemplate;

    public UserService(final UserRepository userRepository,
                       final GroupeService groupeService,
                       final LdapTemplate ldapTemplate) {
        this.userRepository = userRepository;
        this.groupeService = groupeService;
        this.ldapTemplate = ldapTemplate;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Méthode permettant de convertir récupérer les noms des groupes d'une List<Groupe>
     * Création d'un Set<String> qui pourra être passé à un utilisateur
     *
     * @param groupes List<Groupes>
     * @return Set<String>
     */
    public Set<String> recupererRoles(final List<Groupe> groupes) {
        Set<String> roles = new HashSet<>();
        for (Groupe groupe : groupes) {
            roles.add(groupe.getNom());
        }
        return roles;
    }

    /**
     * findOne dans le repo. ldapRepository prend en paramètre une "query"
     * Cette dernière doit être construite à l'aide de LdapQueryBuilder
     * Si l'utilisateur existe, on récupère ses groupes via le Service des groupes
     * On convertit la liste en Set de Strings et on l'attribue à l'utilisateur qu'on retourne
     *
     * @param id <String>
     * @return Optional<User>
     */
    public Optional<User> findByUid(final String id) {
        LdapQuery query = LdapQueryBuilder.query()
                                          .where("objectclass")
                                          .is("person")
                                          .and("uid")
                                          .is(id);
        final Optional<User> userRecherche = userRepository.findOne(query);
        userRecherche.ifPresent(user -> {
            final List<Groupe> groupesUser = groupeService.findUserGroup(user.getDn());
            user.setRoles(recupererRoles(groupesUser));
        });
        return userRecherche;
    }

    /**
     * Modifie les groupes du LDIF en fonction de la requête client
     * @param user User
     * @return user
     */
    public User modifier(final User user) {
        // On récupère la liste des nouveaux et des anciens groupes de l'utilisateur
        Set<String> nouveauxRoles = new HashSet<>(user.getRoles());
        Set<String> anciensRoles = recupererRoles(
                groupeService.findUserGroup(user.getDn()));
        // On identifie les roles à rajouter
        Set<String> rolesARajouter = new HashSet<>(user.getRoles());
        rolesARajouter.removeAll(anciensRoles);
        // On identifie les roles à supprimer
        Set<String> rolesASupprimer = new HashSet<>(recupererRoles(
                groupeService.findUserGroup(user.getDn())));
        rolesASupprimer.removeAll(nouveauxRoles);

        // On modifie le LDIF en conséquence
        for(String cn : rolesARajouter) {
            List<Groupe> groupeAjout = new ArrayList<>(groupeService.findGroupByName(cn));
            groupeAjout.get(0).addMembre(user.getDnInName());
            ldapTemplate.update(groupeAjout.get(0));
        }
        for(String cn : rolesASupprimer) {
            List<Groupe> groupeRetrait = new ArrayList<>(groupeService.findGroupByName(cn));
            groupeRetrait.get(0).removeMembre(user.getDnInName());
            ldapTemplate.update(groupeRetrait.get(0));
        }
         return user;
    }
}

