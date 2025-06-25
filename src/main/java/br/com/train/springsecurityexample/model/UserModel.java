package br.com.train.springsecurityexample.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_users")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "user_name")
    private String username;

    private String fullName;

    private String password;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    private boolean enabled;

    @Column(name = "active",nullable = true)
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_permission",
            joinColumns ={ @JoinColumn(name = "id_user") },
            inverseJoinColumns = {@JoinColumn(name = "id_permission") })
    private List<PermisionModel> permissions;

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (PermisionModel permission : this.permissions) {
            roles.add(permission.getDescription());
        }
        return roles;

    }

    public UserModel() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PermisionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermisionModel> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return accountNonExpired == userModel.accountNonExpired && accountNonLocked == userModel.accountNonLocked && credentialsNonExpired == userModel.credentialsNonExpired && enabled == userModel.enabled && active == userModel.active && Objects.equals(id, userModel.id) && Objects.equals(username, userModel.username) && Objects.equals(fullName, userModel.fullName) && Objects.equals(password, userModel.password) && Objects.equals(permissions, userModel.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, fullName, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled, active, permissions);
    }
}
