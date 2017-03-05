package com.my.crossy.road.component.generic;

import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.assetManager.ModelManager;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class GenericGraphicComposant extends GraphicsComponent {

    private static final String TAG = GenericGraphicComposant.class.getSimpleName();
    private Json _json = new Json();

    private ModelInstance _3Dmodel = null;

    private ModelManager _modelManager = ModelManager.getInstance();

    @Override
    public void receiveMessage(String message) {
        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                TypeLigneAffichage typeLigneAffichage = _json.fromJson(TypeLigneAffichage.class, messageReceived[1]);
                Vector3 vector3 = _json.fromJson(Vector3.class, messageReceived[2]);
                Float size = _json.fromJson(Float.class, messageReceived[3]);
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_GRAPHICS.toString() + " reveived with typeLigneAffichage : "+typeLigneAffichage.name()+
                        ", vector3" + vector3.toString() + ", size" + size);
                Color couleur = null;
                switch (typeLigneAffichage){
                    case Arbre:
                        couleur = Color.GREEN;
                        break;
                    case Eau:
                        couleur = Color.BLUE;
                        break;
                    case Route:
                        couleur = Color.GRAY;
                        break;
                    case Train:
                        couleur = Color.RED;
                        break;
                    default:
                        couleur = Color.BLACK;
                        break;
                }
                _3Dmodel = _modelManager.getCubeFromColor(couleur, size).copy();

                //translate
                _3Dmodel.transform.translate(vector3);
            } else if(messageReceived[0].equalsIgnoreCase(MESSAGE.ENVIRONNEMENT_MOVE.toString())){
                Float positionMin = _json.fromJson(Float.class, messageReceived[1]);
                Direction direction = _json.fromJson(Direction.class, messageReceived[2]);
                Gdx.app.debug(TAG, "Message " + MESSAGE.ENVIRONNEMENT_MOVE.toString() + " reveived with positionMin : " + positionMin +
                         ", direction : " + direction.toString());
                switch (direction){
                    case UP:
                        _3Dmodel.transform.translate(new Vector3(0, 0,-Configuration.TAILLE_BLOC.get_valeur()));
                        break;
                    case DOWN:
                        _3Dmodel.transform.translate(new Vector3(0, 0, Configuration.TAILLE_BLOC.get_valeur()));
                        break;
                    case LEFT:
                        _3Dmodel.transform.translate(new Vector3(-Configuration.TAILLE_BLOC.get_valeur(), 0,0));
                        break;
                    case RIGHT:
                        _3Dmodel.transform.translate(new Vector3(Configuration.TAILLE_BLOC.get_valeur(), 0,0));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, Environment environment) {
        batch.begin(camera);
        batch.render(_3Dmodel);
        batch.end();

        Vector3 position = _3Dmodel.transform.getTranslation(new Vector3());
        if (position.z < Configuration.POSITION_MIN_ENVIRONNEMENT.get_valeur()){
            entity.set_isDetroyable();
        }

        //For hitbox debugging
        /**
        if(entity.get_physicsComponent() != null){
            ModelInstance modelInstance = _modelManager.getCubeFromColor(Color.CYAN, Configuration.TAILLE_BLOC.get_valeur());
            Vector2 hitBoxPos = entity.get_physicsComponent().getHitbox().getPosition(new Vector2());
            modelInstance.transform.translate(hitBoxPos.x, 0, hitBoxPos.y);
            batch.begin(camera);
            batch.render(modelInstance);
            batch.end();
        }
         **/

        entity.set_position(position);

    }
}
