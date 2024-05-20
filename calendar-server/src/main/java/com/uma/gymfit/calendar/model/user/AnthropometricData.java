package com.uma.gymfit.calendar.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnthropometricData {

    private double height;

    private double weight;

    private int caloriesBurned;

    private List<Weight> listUserWeight;

    private List<FatPercentage> listFatPercentage;
}
