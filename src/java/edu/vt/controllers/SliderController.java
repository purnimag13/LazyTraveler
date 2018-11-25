/*
 * Created by Purnima Ghosh on 2018.10.14  * 
 * Copyright © 2018 Purnima Ghosh. All rights reserved. * 
 */
package edu.vt.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "sliderController")
@RequestScoped

public class SliderController {

    // Each String object in the List contains the image filename, e.g., photo1.png
    private List<String> sliderImages;

    /*
    The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization. The initialization 
    method init() is the first method invoked before this class is put into service. 
    */
    @PostConstruct
    public void init() {

        sliderImages = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            sliderImages.add("photo" + i + ".png");
        }
    }

    /*
    =============
    Getter Method
    =============
     */
    public List<String> getSliderImages() {
        return sliderImages;
    }
    
    /*
    ===============
    Instance Method
    ===============
     */
    public String description(String image) {

        String imageDescription = "";

        switch (image) {
            case "photo1.png":
                imageDescription = "Roasted Beet and Citrus Salad With Ricotta and Pistachio Vinaigrette";
                break;
            case "photo2.png":
                imageDescription = "Shrimp and Pasta Primavera";
                break;
            case "photo3.png":
                imageDescription = "Vanilla Cake";
                break;
            case "photo4.png":
                imageDescription = "Chicken with Sourdough-Mushroom Stuffing";
                break;
            case "photo5.png":
                imageDescription = "The Best Minestrone Soup Recipe";
                break;
        }

        return imageDescription;
    }
}
