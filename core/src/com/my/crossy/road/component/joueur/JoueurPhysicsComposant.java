package com.my.crossy.road.component.joueur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;

/**
 * Created by ldalzotto on 26/02/2017.
 */
public class JoueurPhysicsComposant extends PhysicsComponent {

    private static final String TAG = JoueurPhysicsComposant.class.getSimpleName();
    private Json json = new Json();

    @Override
    public void update(Entity entity, float delta) {
        //ne rien faire, la hitbox du joueur ne change pas et ne bouge pas
    }

    @Override
    public void receiveMessage(String message) {
        if (message == null)
            return;

        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);
        if(messageReceived.length > 1 &&
                messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_HITBOX.toString())){
            Vector3 vector3 = json.fromJson(Vector3.class, messageReceived[1]);
            Float size = json.fromJson(Float.class, messageReceived[2]);
            Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_HITBOX.toString() + " reveived with " +
                  "vector3" + vector3.toString() + ", size" + size);
            hitBox = new Rectangle(vector3.x, vector3.z, size, size);
        }

    }
}
