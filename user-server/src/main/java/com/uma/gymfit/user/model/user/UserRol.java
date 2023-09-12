package com.uma.gymfit.user.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Roles")
public class UserRol implements Serializable {
    
    @Id
    private String id;

    private String nameRole;

    private RoleList roleList;
}
