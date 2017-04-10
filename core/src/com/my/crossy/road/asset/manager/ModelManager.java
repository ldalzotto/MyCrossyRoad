package com.my.crossy.road.asset.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.List;

/**
 * Created by ldalzotto on 03/02/2017.
 */
public class ModelManager extends AssetManager {

    private static final String TAG = ModelManager.class.getSimpleName();

    private static ModelManager instance = null;

    private ModelManager(){
        super();
    }

    public static ModelManager getInstance(){
        if(instance == null){
            instance = new ModelManager();
        }
        return instance;
    }

    public void loadModels(List<String> path){
        path.forEach(pathString -> {
            Gdx.app.debug(TAG, "Loading model : " + pathString);
            this.load(pathString, Model.class);
        });
        finishLoading();
    }

    public ModelInstance getCubeFromColor(Color color, Float taille){
        ModelBuilder modelBuilder = new ModelBuilder();
        return new ModelInstance(modelBuilder.createBox(taille, taille, taille, new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

}
