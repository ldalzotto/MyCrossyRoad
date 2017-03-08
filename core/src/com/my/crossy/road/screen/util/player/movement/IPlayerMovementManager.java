package com.my.crossy.road.screen.util.player.movement;

import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;

import java.util.List;

/**
 * Created by ldalzotto on 06/03/2017.
 */
public interface IPlayerMovementManager {

    public Boolean isAbleToCreateNewBlocs(List<Entity> blocs, Entity joueur);
    public void updatePlayerWidthIndex(Direction direction);
    public Integer getPlayerWidthIndex();

}
