package com.my.crossy.road.component.joueur;

import INTEnvironnementManager.INTEnvironnementManager;
import INTEnvironnementManager.interfaceManger.IINTEnvironnementManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;

import java.util.List;

/**
 * Created by ldalzotto on 26/02/2017.
 */
public class JoueurPhysicsComposant extends PhysicsComponent {

    private static final String TAG = JoueurPhysicsComposant.class.getSimpleName();
    private Json _json = new Json();

    @Override
    public void update(Entity entity, float delta) {

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
            }
        }

    }
}
