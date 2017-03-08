package com.my.crossy.road.configuration;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public enum Configuration {

    TAILLE_BLOC(5.0f),
    TAILLE_JOUEUR(5.0f),
    POSITION_MIN_ENVIRONNEMENT(-30.0f), //correspond à la coordonnée à partir de laquelle les blocs seront supprimés
    ENVIRONNEMENT_SPEED(1.0f);

    private Float _valeur;

    Configuration(Float valeur){
        _valeur = valeur;
    }

    public Float get_valeur(){
        return _valeur;
    }

}
