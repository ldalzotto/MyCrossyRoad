package com.my.crossy.road.configuration;

/**
 * Created by ldalzotto on 06/03/2017.
 */
public enum PlayerConfiguration {

    MAX_LINES_NB_TO_CREATE_NEW_BLOC(3); //correspond au nombre de lignes max que le joueur peut avancer sans créer de nouvelles lignes

    private Integer _value;

    PlayerConfiguration(Integer value){
        _value = value;
    }

    public Integer getValue(){
        return _value;
    }

}
