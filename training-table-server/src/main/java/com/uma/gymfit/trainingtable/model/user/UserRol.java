package com.uma.gymfit.trainingtable.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRol implements Serializable {

    @Id
    private String id;

    private String nameRole;

    private RoleList roleList;
}
