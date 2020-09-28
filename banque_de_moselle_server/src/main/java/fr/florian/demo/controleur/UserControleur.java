package fr.florian.demo.controleur;

import fr.florian.demo.form.UserForm;
import fr.florian.demo.modele.User;
import fr.florian.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserControleur {
    private final UserService userService;

    public UserControleur(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/actuel")
    public LdapUserDetails getActuel(final Authentication authentication) {
        return (LdapUserDetails) authentication.getPrincipal();
    }

    @GetMapping
    @Secured("ROLE_ADMINS")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping(value = "{id}")
    @Secured("ROLE_ADMINS")
    public ResponseEntity<User> getOne(final @PathVariable String id) {
        return userService.findByUid(id)
                          .map(user -> ResponseEntity.ok().body(user))
                          .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "{id}")
    @Secured("ROLE_ADMINS")
    public ResponseEntity<User> modifier(final @PathVariable String id,
                                         final @Valid @RequestBody UserForm userForm) {
        return userService.findByUid(id)
                                 .map(userAModifier -> {
                                     userAModifier.setRoles(userForm.getRoles());
                                     return ResponseEntity.ok().body(userService.modifier(userAModifier));
                                 })
                                 .orElse(ResponseEntity.notFound().build());
    }
}
