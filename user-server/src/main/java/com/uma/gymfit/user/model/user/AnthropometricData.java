package com.uma.gymfit.user.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class AnthropometricData implements Serializable {

    private double height;

    private double weight;

    private int caloriesBurned;

    private int heartRate;

    private List<Weight> listUserWeight;

    private List<FatPercentage> listFatPercentage;
}
