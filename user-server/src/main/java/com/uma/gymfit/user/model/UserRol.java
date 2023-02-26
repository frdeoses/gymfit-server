package com.uma.gymfit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Roles")
public class UserRol {

    @Id
    private String id;

    private String nameRole;

    private RoleList roleList;
}
