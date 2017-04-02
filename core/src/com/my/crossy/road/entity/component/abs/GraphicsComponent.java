package com.my.crossy.road.entity.component.abs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.screen.util.MovePositionHandler;


/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class GraphicsComponent implements Component {

    protected MovePositionHandler movePositionHandler = null;
    protected ModelInstance model3D = null;

    public abstract void update(Entity entity, ModelBatch batch, Camera camera, Environment environment, float delta);

    public ModelInstance getModel3D() {
        return model3D;
    }

    public MovePositionHandler getMovePositionHandler() {
        return movePositionHandler;
    }
}
