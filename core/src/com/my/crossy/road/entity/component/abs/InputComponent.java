package com.my.crossy.road.entity.component.abs;

import com.badlogic.gdx.graphics.Camera;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;


/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class InputComponent implements Component {

    public abstract void update(Entity entity, Camera camera, float delta);


}
