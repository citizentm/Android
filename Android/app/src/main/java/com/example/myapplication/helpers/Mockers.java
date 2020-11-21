package com.example.myapplication.helpers;

import com.example.myapplication.models.ProblemModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Mockers {
    private static Mockers mockers;

    public static Mockers getInstance(){
        if (mockers == null){
            mockers = new Mockers();
        }
        return mockers;
    }

    public List<ProblemModel> mockMarkersList(){
        List<ProblemModel> problemsList = new ArrayList<>();
        ArrayList<String> photoList = new ArrayList<>();
        photoList.add("https://firebasestorage.googleapis.com/v0/b/citizentm.appspot.com/o/problems%2F1605970465717.jpg?alt=media&token=2df4e77e-4da7-4544-9c34-41ec35cfd531");
        photoList.add("https://firebasestorage.googleapis.com/v0/b/citizentm.appspot.com/o/problems%2F1605970465777.jpg?alt=media&token=4a6f7d0a-4326-49da-9911-1af448564cf7");
        photoList.add("https://firebasestorage.googleapis.com/v0/b/citizentm.appspot.com/o/problems%2F1605971282208.jpg?alt=media&token=f74fac29-95fa-40e3-8799-f082f81eff12");

        problemsList.add(new ProblemModel(1, new LatLng(45.7518239, 21.2221786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, false));
        problemsList.add(new ProblemModel(2, new LatLng(45.7528239, 21.2321786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, true));
        problemsList.add(new ProblemModel(3, new LatLng(45.7498239, 21.2341786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, true));
        problemsList.add(new ProblemModel(4, new LatLng(45.7538239, 21.2431786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, false));
        problemsList.add(new ProblemModel(5, new LatLng(45.7508239, 21.2431786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, false));
        problemsList.add(new ProblemModel(6, new LatLng(45.7508239, 21.2451786), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.", "12-03-2020", photoList, true));

        return problemsList;
    }
}
