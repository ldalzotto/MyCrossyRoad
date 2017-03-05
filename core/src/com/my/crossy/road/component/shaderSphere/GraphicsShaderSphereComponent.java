package com.my.crossy.road.component.shaderSphere;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.my.crossy.road.assetManager.ModelManager;
import com.my.crossy.road.component.shader.TestShader;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.Component;
import com.my.crossy.road.entity.component.abs.GraphicsComponent;

/**
 * Created by ldalzotto on 05/02/2017.
 */
public class GraphicsShaderSphereComponent extends GraphicsComponent {


    private static final String TAG = GraphicsShaderSphereComponent.class.getSimpleName();

    private ModelManager _modelManager = ModelManager.getInstance();

    private Json _json = null;
    private ModelInstance _cubeInstance = null;

    private Renderable _renderable = null;
    private RenderContext _renderContext = null;
    private Shader _shader = null;

    private String _modelLocation = null;

    public GraphicsShaderSphereComponent(){
        _json = new Json();
    }

    @Override
    public void receiveMessage(String message) {
        String[] messageReceived = message.split(Component.MESSAGE_TOKEN);

        if(messageReceived.length > 1){
            if(messageReceived[0].equalsIgnoreCase(MESSAGE.INIT_GRAPHICS.toString())){
                Gdx.app.debug(TAG, "Message " + MESSAGE.INIT_GRAPHICS.toString() + " reveived.");
                ModelBuilder modelBuilder = new ModelBuilder();
                Model model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20, new Material(),
                        VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

                _cubeInstance = new ModelInstance(model, 0, 0, 0);

                NodePart blockPart = _cubeInstance.nodes.get(0).parts.get(0);

                _renderable = new Renderable();
                blockPart.setRenderable(_renderable);
                _renderable.environment = null;
                _renderable.worldTransform.idt();

                _renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
                _shader = new TestShader();
                _shader.init();
            }
        }
    }

    @Override
    public void update(Entity entity, ModelBatch batch, Camera camera, Environment environment) {
        batch.begin(camera);
        _shader.begin(camera, _renderContext);
        batch.render(_cubeInstance, _shader);
        _shader.end();
        batch.end();
    }
}
