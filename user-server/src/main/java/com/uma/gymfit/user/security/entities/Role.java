package com.uma.gymfit.user.security.entities;

import com.uma.gymfit.user.security.enums.RoleList;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
public class Role {

    @Id
    private int id;

     @NotNull
    private RoleList roleName;

}
