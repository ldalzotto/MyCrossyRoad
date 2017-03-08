package com.my.crossy.road.configuration;

/**
 * Created by ldalzotto on 06/03/2017.
 */
public enum PlayerConfiguration {

    MAX_BLOX_INDEX_TO_CREATE_NEW_BLOC(3);

    private Integer _value;

    PlayerConfiguration(Integer value){
        _value = value;
    }

    public Integer getValue(){
        return _value;
    }

}
