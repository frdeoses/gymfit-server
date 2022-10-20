package com.uma.gymfit.user.security.entities;

import com.uma.gymfit.user.security.enums.RoleList;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(value = "Role")
@Data
public class Role {

    @Id
    private int id;

     @NotNull
    private RoleList roleName;

    public Role(@NotNull RoleList roleName) {
        this.roleName = roleName;
    }
}
