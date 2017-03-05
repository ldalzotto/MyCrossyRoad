package com.my.crossy.road.component.generic;

import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
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
    private Json _json = new Json();

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void receiveMessage(String message) {
        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_HITBOX.toString())){
                Vector3 vector3 = _json.fromJson(Vector3.class, messageReceived[1]);
                Float size = _json.fromJson(Float.class, messageReceived[2]);
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_HITBOX.toString() + " reveived with " +
                        "vector3" + vector3.toString() + ", size" + size);
                _hitBox = new Rectangle(vector3.x, vector3.z, size, size);
            } else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_MOVE.toString())){
                Float positionMin = _json.fromJson(Float.class, messageReceived[1]);
                Direction direction = _json.fromJson(Direction.class, messageReceived[2]);
                Gdx.app.debug(TAG, "Message " + MESSAGE.ENVIRONNEMENT_MOVE.toString() + " reveived with positionMin : " + positionMin +
                        ", direction : " + direction.toString());

                Vector2 position = _hitBox.getPosition(new Vector2());
                switch (direction){
                    case UP:
                        position.add(0, -Configuration.TAILLE_BLOC.get_valeur());
                        break;
                    case DOWN:
                        position.add(0, Configuration.TAILLE_BLOC.get_valeur());
                        break;
                    case LEFT:
                        position.add(-Configuration.TAILLE_BLOC.get_valeur(), 0);
                        break;
                    case RIGHT:
                        position.add(Configuration.TAILLE_BLOC.get_valeur(), 0);
                        break;
                    default:
                        break;
                }

                _hitBox.setPosition(position);
            }  else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_FUTURE_MOVE.toString())){
                Float positionMin = _json.fromJson(Float.class, messageReceived[1]);
                Direction direction = _json.fromJson(Direction.class, messageReceived[2]);
                Gdx.app.debug(TAG, "Message " + MESSAGE.ENVIRONNEMENT_MOVE.toString() + " reveived with positionMin : " + positionMin +
                        ", direction : " + direction.toString());

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
}
