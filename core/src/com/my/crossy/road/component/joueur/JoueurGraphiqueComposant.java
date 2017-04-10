package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.asset.manager.ModelManager;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;

/**
 * Created by ldalzotto on 26/02/2017.
 */
public class JoueurGraphiqueComposant extends GraphicsComponent {

    private static final String TAG = JoueurGraphiqueComposant.class.getSimpleName();
    private Json json = new Json();

    private ModelManager modelManager = ModelManager.getInstance();

    @Override
    public void receiveMessage(String message) {
        if (message == null)
            return;

        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_GRAPHICS.toString() + " reveived.");
                Vector3 position = json.fromJson(Vector3.class, messageReceived[2]);
                Float taille = json.fromJson(Float.class, messageReceived[3]);
                model3D = modelManager.getCubeFromColor(Color.PINK, taille);
                model3D.transform.translate(position);
            } else if(messageReceived[0].equalsIgnoreCase(MESSAGE.PLAYER_MOVE_FORWARD.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.PLAYER_MOVE_FORWARD.toString() + " received.");
                model3D.transform.translate(new Vector3(0,0,Configuration.TAILLE_BLOC.get_valeur()));
            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, float delta) {
        batch.begin(camera);
        batch.render(model3D);
        batch.end();

        Vector3 position = model3D.transform.getTranslation(new Vector3());
        entity.setPosition(position);
    }
}
