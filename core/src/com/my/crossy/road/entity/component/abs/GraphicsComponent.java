package com.my.crossy.road.entity.component.abs;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;


/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class GraphicsComponent implements Component {

    public abstract void update(Entity entity, ModelBatch batch, Camera camera, Environment environment);

}
