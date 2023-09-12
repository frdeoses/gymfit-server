package com.uma.gymfit.calendar.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String surname;

    @Indexed(unique = true)
    @NotNull
    @NotBlank
    @Email
    private String email;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String phone;

    @JsonProperty(required = true)
    @NotNull
    private LocalDateTime birthDate;

    @JsonProperty(required = true)
    @NotNull
    private LocalDateTime registrationDate;
    
    private double weight;

    private double height;

    private int caloriesBurned;

    private List<Weight> listUserWeight;

    private List<FatPercentage> listFatPercentage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authorities = new HashSet<>();

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
