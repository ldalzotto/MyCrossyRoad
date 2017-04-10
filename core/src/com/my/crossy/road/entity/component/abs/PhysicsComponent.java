package com.my.crossy.road.entity.component.abs;


import com.badlogic.gdx.math.Rectangle;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.screen.util.MovePositionHandler;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class PhysicsComponent implements Component {

    protected Rectangle hitBox;
    protected MovePositionHandler movePositionHandler = null;

    public abstract void update(Entity entity, float delta);

    public Boolean isInCollitionWith(Entity entity){
        return entity.getPhysicsComponent()
                .getHitbox().overlaps(hitBox);
    }

    public Rectangle getHitbox(){
        return hitBox;
    }

    public MovePositionHandler getMovePositionHandler() {
        return movePositionHandler;
    }
}
