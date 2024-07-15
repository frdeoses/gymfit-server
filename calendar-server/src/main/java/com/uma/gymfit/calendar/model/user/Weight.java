package com.uma.gymfit.calendar.model.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Weight implements Serializable {

    private long date;

    private double weightData;

}
