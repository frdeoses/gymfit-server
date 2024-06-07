package com.uma.gymfit.user.converters;

import com.uma.gymfit.user.model.dto.UserRS;
import com.uma.gymfit.user.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertUserToUserRS {

    public UserRS convert(User user) {
        return createUserRS(user);
    }

    public UserRS convert(UserDetails userDetails) {
        User user = (User) userDetails;
        return createUserRS(user);
    }

    public List<UserRS> convert(List<User> users) {
        return users.stream()
                .map(this::createUserRS)
                .collect(Collectors.toList());

    }

    private UserRS createUserRS(User user) {
        return UserRS.builder()
                .id(user.getId())
                .listFatPercentage(user.getAnthropometricData().getListFatPercentage())
                .heartRate(user.getAnthropometricData().getHeartRate())
                .caloriesBurned(user.getAnthropometricData().getCaloriesBurned())
                .phone(user.getPersonalData().getPhone())
                .email(user.getPersonalData().getEmail())
                .listUserWeight(user.getAnthropometricData().getListUserWeight())
                .name(user.getPersonalData().getName())
                .surname(user.getPersonalData().getSurname())
                .birthDate(user.getPersonalData().getBirthDate())
                .height(user.getAnthropometricData().getHeight())
                .weight(user.getAnthropometricData().getWeight())
                .username(user.getUsername())
                .userRoles(user.getUserRoles())
                .build();
    }
}
