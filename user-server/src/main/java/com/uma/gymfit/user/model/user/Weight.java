package com.uma.gymfit.user.model.user;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Weight implements Serializable {

    private LocalDateTime date;

    private double weightData;

}
