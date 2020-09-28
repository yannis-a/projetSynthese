package fr.florian.demo.form;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class UserForm {

    @NotEmpty
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }
}
