package com.uma.gymfit.service;

import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;

import java.net.http.HttpResponse;
import java.util.List;

public interface IGymFitService<T extends TablaEntrenamiento, U extends Usuario> {

    public List<U> allUser();

    public List<T> allTrainingTable();

    public void createUser(U user);

    public void createTrainingTable(T trainingT);

    public void deleteUser(String id);

    public void deleteTrainingTable(String id);

    public U updateUser(U user);

    public T updateTrainingTable(T trainingT);
}
