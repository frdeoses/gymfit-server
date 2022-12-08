package com.uma.gymfit.calendar.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRol {

    @Id
    private String id;

    private String nameRole;

    private RoleList roleList;
}
