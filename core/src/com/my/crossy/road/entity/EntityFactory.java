package com.my.crossy.road.entity;


import com.my.crossy.road.component.generic.GenericGraphicComposant;
import com.my.crossy.road.component.generic.GenericPhysicComposant;
import com.my.crossy.road.component.graphics.EnvironnementGraphiqueComponent;
import com.my.crossy.road.component.joueur.JoueurGraphiqueComposant;
import com.my.crossy.road.component.joueur.JoueurInputComposant;
import com.my.crossy.road.component.joueur.JoueurPhysicsComposant;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class EntityFactory {

    public static Entity getEntity(Entity.EntityType entityType){
        Entity entity = null;

        switch (entityType){
            case PLAYER:
                entity = new Entity(new JoueurInputComposant(), new JoueurGraphiqueComposant(), new JoueurPhysicsComposant());
                break;
            case ENVIRONNEMENT:
                entity = new Entity(null, new EnvironnementGraphiqueComponent(), null);
                break;
            case BLOC_OBSTACLE:
                entity = new Entity(null, new GenericGraphicComposant(), new GenericPhysicComposant());
                break;
            case BLOC_DECOR:
                entity = new Entity(null, new GenericGraphicComposant(), null);
                break;
            default:
                break;
        }

        return entity;
    }

}
