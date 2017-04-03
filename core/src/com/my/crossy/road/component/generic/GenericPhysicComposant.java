package com.my.crossy.road.component.generic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.component.util.MovePositionHandlerCreator;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class GenericPhysicComposant extends PhysicsComponent {

    private static final String TAG = GenericPhysicComposant.class.getSimpleName();
    private Json json = new Json();

    @Override
    public void receiveMessage(String message) {
        if (message == null)
            return;

        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_HITBOX.toString())){
                Vector3 vector3 = json.fromJson(Vector3.class, messageReceived[1]);
                Float size = json.fromJson(Float.class, messageReceived[2]);
                _hitBox = new Rectangle(vector3.x, vector3.z, size, size);
            } else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_MOVE.toString())){
                Direction direction = json.fromJson(Direction.class, messageReceived[2]);
                //si le dernier mouvement est terminé
                if(_movePositionHandler == null){
                    Gdx.app.debug(TAG, "The last movement is terminated, start another one.");
                    _movePositionHandler = MovePositionHandlerCreator.createHandler(direction, _hitBox);
                }
            }  else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.toString())){
                Direction direction = json.fromJson(Direction.class, messageReceived[2]);

                Vector2 position = _hitBox.getPosition(new Vector2());
                switch (direction){
                    case UP:
                        position.add(0, -Configuration.TAILLE_BLOC.get_valeur()/2);
                        break;
                    case DOWN:
                        position.add(0, Configuration.TAILLE_BLOC.get_valeur()/2);
                        break;
                    case LEFT:
                        position.add(-Configuration.TAILLE_BLOC.get_valeur()/2, 0);
                        break;
                    case RIGHT:
                        position.add(Configuration.TAILLE_BLOC.get_valeur()/2, 0);
                        break;
                    default:
                        break;
                }

                _hitBox.setPosition(position);
            }
        }
    }


    @Override
    public void update(Entity entity, float delta) {

        if(_movePositionHandler != null){
            entity.isMoving();
            Vector3 actualPosition = _movePositionHandler.updatePosition(delta);
            _hitBox.setPosition(actualPosition.x, actualPosition.z);
            _movePositionHandler = _movePositionHandler.reEvaluate();
        } else {
            entity.hasMoved();
        }

    }
}
