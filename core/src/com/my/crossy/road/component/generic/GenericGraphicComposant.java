package com.my.crossy.road.component.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.assetManager.ModelManager;
import com.my.crossy.road.component.util.MovePositionHandlerCreator;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;
import common.enumeration.TypeLigne;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class GenericGraphicComposant extends GraphicsComponent {

    private static final String TAG = GenericGraphicComposant.class.getSimpleName();
    private Json json = new Json();

    private ModelManager modelManager = ModelManager.getInstance();

    @Override
    public void receiveMessage(String message) {
        if(message == null)
            return;

        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                TypeLigne typeLigneAffichage = json.fromJson(TypeLigne.class, messageReceived[1]);
                Vector3 initialPosition = json.fromJson(Vector3.class, messageReceived[2]);
                Float size = json.fromJson(Float.class, messageReceived[3]);
                model3D = modelManager.getCubeFromColor(typeLigneAffichage.getCouleurCube(), size).copy();

                //translate
                model3D.transform.translate(initialPosition);
            } else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_MOVE.toString())){
                Direction direction = json.fromJson(Direction.class, messageReceived[2]);

                //si le dernier mouvement est terminé
                if(movePositionHandler == null){
                    Gdx.app.debug(TAG, "The last movement is terminated, start another one.");
                   movePositionHandler = MovePositionHandlerCreator.createHandler(direction, model3D);
                }
            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, Environment environment, float delta) {

        if(movePositionHandler != null){
            entity.isMoving();
            model3D.transform.setTranslation(movePositionHandler.updatePosition(delta));
            movePositionHandler = movePositionHandler.reEvaluate();
        } else {
            entity.hasMoved();
        }

        batch.begin(camera);
        batch.render(model3D);
        batch.end();

        Vector3 position = model3D.transform.getTranslation(new Vector3());
        if (position.z < Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()){
            entity.set_isDetroyable();
        }

        entity.set_position(position);

    }
}
