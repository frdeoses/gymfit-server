package com.uma.gymfit.user.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uma.gymfit.user.model.security.Authority;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(value = "User")
@Data
public class User implements UserDetails {

    @Id
    private String id;

    @JsonProperty(required = true)
    @Indexed(unique = true)
    @NotNull
    @NotBlank
    private String username;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String password;

    @JsonProperty(required = true)
    @NotNull
    private Set<UserRol> userRoles;

    private PersonalData personalData;

    private LocalDateTime registrationDate;

    private AnthropometricData anthropometricData;

    public User(final String username,
                final String password,
                final PersonalData personalData,
                final AnthropometricData anthropometricData) {
        this.username = username;
        this.password = password;
        this.personalData = personalData;
        this.anthropometricData = anthropometricData;
        userRoles = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authorities = new HashSet<>();

        if (userRoles.isEmpty()) {
            userRoles = new HashSet<>();
            userRoles.add(new UserRol(UUID.randomUUID().toString(), RoleList.USER.toString(), RoleList.USER));
        }

        this.userRoles.forEach(userRol -> {
            Authority authority = new Authority(userRol.getRoleList().name());
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
