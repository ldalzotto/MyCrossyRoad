package com.my.crossy.road.entity.component.abs;


import com.badlogic.gdx.math.Rectangle;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class PhysicsComponent implements Component {

    protected Rectangle _hitBox;

    public abstract void update(Entity entity);

    public Boolean isInCollitionWith(Entity entity){
        return entity.get_physicsComponent()
                .getHitbox().overlaps(_hitBox);
    }

    public Rectangle getHitbox(){
        return _hitBox;
    }

}
