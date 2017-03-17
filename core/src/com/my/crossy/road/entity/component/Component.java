package com.my.crossy.road.entity.component;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public interface Component {

    String MESSAGE_TOKEN = ";;;;;;;;;;;;";

    enum MESSAGE{
        SET_MODEL_PATH,
        INIT_GRAPHICS,
        INIT_HITBOX,
        PLAYER_MOVE_FORWARD,
        MOVE_DONE,
        ENVIRONNEMENT_MOVE,
        ENVIRONNEMENT_FUTURE_MOVE;
    }
    
    void receiveMessage(String message);

}
