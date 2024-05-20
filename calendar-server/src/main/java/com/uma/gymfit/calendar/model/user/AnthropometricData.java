package com.uma.gymfit.calendar.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class AnthropometricData implements Serializable {

    private static final long serialVersionUID = 1764177798999036118L;

    private double height;

    private double weight;

    private int caloriesBurned;

    private List<Weight> listUserWeight;

    private List<FatPercentage> listFatPercentage;
}
