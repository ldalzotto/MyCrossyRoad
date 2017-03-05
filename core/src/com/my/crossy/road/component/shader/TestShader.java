package com.my.crossy.road.component.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by ldalzotto on 05/02/2017.
 */
public class TestShader implements Shader {

    private ShaderProgram _shaderProgram = null;
    private Camera _camera = null;
    private RenderContext _renderContext = null;

    private int _u_projViewTrans;
    private int _u_worldTrans;

    @Override
    public void init() {
        String vert = Gdx.files.internal("D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\shader\\test.vertex.glsl").readString();
        String frag = Gdx.files.internal("D:\\Profiles\\ldalzotto\\IdeaProjects\\MyCrossyRoad\\core\\assets\\shader\\test.fragment.glsl").readString();
        _shaderProgram = new ShaderProgram(vert, frag);
        if(!_shaderProgram.isCompiled()){
            throw new GdxRuntimeException(_shaderProgram.getLog());
        }
    }

    @Override
    public int compareTo(Shader other) {
        return 0;
    }

    @Override
    public boolean canRender(Renderable instance) {
        return false;
    }

    @Override
    public void begin(Camera camera, RenderContext context) {
        _camera = camera;
        _renderContext = context;
        _shaderProgram.begin();
        _shaderProgram.setUniformMatrix("u_projViewTrans", _camera.combined);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        _renderContext.setDepthTest(GL20.GL_LEQUAL);
        _renderContext.setCullFace(GL20.GL_BACK);
    }

    @Override
    public void render(Renderable renderable) {
        _shaderProgram.setUniformMatrix("u_worldTrans", renderable.worldTransform);
        renderable.meshPart.render(_shaderProgram);
    }

    @Override
    public void end() {
        _shaderProgram.end();
    }

    @Override
    public void dispose() {
        _shaderProgram.dispose();
    }
}
