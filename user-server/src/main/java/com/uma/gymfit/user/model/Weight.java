package com.uma.gymfit.user.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Weight {

    private LocalDateTime date;

    private double weight;

}
