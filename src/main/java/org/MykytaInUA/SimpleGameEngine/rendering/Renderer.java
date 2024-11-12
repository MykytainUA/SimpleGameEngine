package org.MykytaInUA.SimpleGameEngine.rendering;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.objects.CubeGenerator;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.ObjectsStorage;
import org.MykytaInUA.SimpleGameEngine.rendering.shaders.ShaderProgram;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputNEWTActionListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputSwingAWTActionListener;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;
import org.MykytaInUA.SimpleGameEngine.window.GameEngineWindow;

import static com.jogamp.opengl.GL4.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;


public class Renderer implements GLEventListener {
	
	private GameEngineWindow window;
	
	private ShaderProgram shaderProgram;
	
	private ObjectsStorage<Object3D> cubes = new ObjectsStorage<Object3D>();
	
	public Renderer(GameEngineWindow window) {
		this.window = window;
	}

	@Override
	public void init(GLAutoDrawable drawable) {	
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glFinish();
		
		shaderProgram = new ShaderProgram(".\\src\\main\\java\\shaders\\vertexShader.glsl", 
										  ".\\src\\main\\java\\shaders\\fragmentShader.glsl", 
										  2,
										  5);
		
		this.cubes.setObjects(CubeGenerator.createRandomCubes(2000, 12, 1000, 1));
		
		gl.setSwapInterval(0);
		shaderProgram.addObjects(this.cubes);
		shaderProgram.prepareShaderPrograms();
		
		shaderProgram.addCamera(window.getCamera());
		
		gl.glClearColor(0.2f, 0.2f, 0.0f, 1.0f);
		
		gl.glEnable(GL_DEPTH_TEST);	
		gl.glDepthFunc(GL_LESS);
		gl.glDisable(GL_BLEND);
		
		gl.glEnable(GL_CULL_FACE);
		gl.glCullFace(GL_BACK);
		gl.glFrontFace(GL_CCW);
		
		Utils.checkOpenGLErrors();
		
		window.resizeWindow(window.getWindowDimention());
		
		gl.glViewport(0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
	}
	
	public static int frame = 0;
	@Override
	public void display(GLAutoDrawable drawable) {
		window.getUserInputListener().updateActionsOnObjects();
		
		GL4 gl = (GL4) GLContext.getCurrentGL();

		
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
		shaderProgram.render();
		
		Utils.checkOpenGLErrors();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL4 gl = drawable.getGL().getGL4();
		
		gl.glViewport(0, 0, width, height);
		
		window.getCamera().setAspect(width, height);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
