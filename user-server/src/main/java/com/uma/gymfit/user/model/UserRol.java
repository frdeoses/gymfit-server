package com.uma.gymfit.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Roles")
@Data
public class UserRol {

    @Id
    private String id;

    private String nameRole;

    private RoleList roleList;
}
