package com.my.crossy.road.component.sky;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.assetManager.ModelManager;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;

/**
 * Created by ldalzotto on 05/02/2017.
 */
public class SkyGraphicsComponent extends GraphicsComponent {
    private static final String TAG = SkyGraphicsComponent.class.getSimpleName();

    private ModelManager _modelManager = ModelManager.getInstance();

    private Json _json = null;
    private ModelInstance _cubeInstance = null;

    private String _modelLocation = null;

    public SkyGraphicsComponent(){
        _json = new Json();
    }

    @Override
    public void receiveMessage(String message) {
        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_GRAPHICS.toString() + " reveived.");
                Vector3 position = _json.fromJson(Vector3.class, messageReceived[1]);
                _cubeInstance = _modelManager.createInstance(_modelLocation, position);
            } else if (messageReceived[0].equalsIgnoreCase(MESSAGE.SET_MODEL_PATH.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.SET_MODEL_PATH.toString() + " reveived.");
                _modelLocation = messageReceived[1];
            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, Environment environment, float delta) {
        batch.begin(camera);
        batch.render(_cubeInstance);
        batch.end();
    }
}
