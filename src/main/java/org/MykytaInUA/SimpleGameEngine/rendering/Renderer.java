package org.MykytaInUA.SimpleGameEngine.rendering;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;

import org.MykytaInUA.SimpleGameEngine.objects.Cube;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.Object3DRandomGenerator;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;
import org.MykytaInUA.SimpleGameEngine.rendering.shaders.ShaderProgram;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;
import org.MykytaInUA.SimpleGameEngine.window.GameEngineWindow;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static com.jogamp.opengl.GL4.*;

import java.util.ArrayList;

public class Renderer implements GLEventListener {
	
	private GameEngineWindow window;
	
	private ShaderProgram shaderProgram;
	
	public Renderer(GameEngineWindow window) {
		this.window = window;
	}

	@Override
	public void init(GLAutoDrawable drawable) {	
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		Object3D[] objects;
		
		gl.glFinish();
		
		Component[] componentsForShader = new Component[4];
		
		componentsForShader[0] = new PositionComponent(new Vector3f());
		componentsForShader[1] = new RotationComponent(new Vector3f());
		componentsForShader[2] = new SizeComponent(new Vector3f());
		componentsForShader[3] = new SolidColorComponent(new Vector4f());
		
		this.shaderProgram = new ShaderProgram(".\\src\\main\\java\\shaders\\vertexShader.glsl", 
										  ".\\src\\main\\java\\shaders\\fragmentShader.glsl", 
										  componentsForShader,
										  5);
		
		Object3DRandomGenerator generator = new Object3DRandomGenerator();
		
		ArrayList<Component> componentByCopy = new ArrayList<Component>();
		ArrayList<Class<? extends Component>> componentByReference = new ArrayList<Class<? extends Component>>(0);
		
		componentByReference.add(PositionComponent.class);
		componentByReference.add(RotationComponent.class);
		componentByReference.add(SolidColorComponent.class);
		componentByReference.add(SizeComponent.class);	
		
		objects = new Object3D[1000000];
		
		for (int i = 0; i < objects.length; i++) {
			objects[i] = generator.getRandomObject(Cube.class, componentByCopy, componentByReference);
		}

		gl.setSwapInterval(0);
		
		this.shaderProgram.addObjects(objects);
		this.shaderProgram.addCamera(window.getCamera());
		
		gl.glClearColor(0.2f, 0.2f, 0.0f, 1.0f);
		
		gl.glEnable(GL_DEPTH_TEST);	
		gl.glDepthFunc(GL_LESS);
		gl.glDisable(GL_BLEND);
		
		gl.glEnable(GL_CULL_FACE);
		gl.glCullFace(GL_BACK);
		gl.glFrontFace(GL_CCW);
		
		Utils.checkOpenGLErrors();
		
		this.window.resizeWindow();
		
		gl.glViewport(0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
	}
	
	public static int frame = 0;
	@Override
	public void display(GLAutoDrawable drawable) {
		
		this.window.getUserInputListener().updateActionsOnObjects();
		GL4 gl = (GL4) GLContext.getCurrentGL();

		
		gl.glClear(GL_COLOR_BUFFER_BIT);
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
		this.shaderProgram.render();
		
		Utils.checkOpenGLErrors();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL4 gl = drawable.getGL().getGL4();
		
		gl.glViewport(0, 0, width, height);
		
		this.window.getCamera().setAspect(width, height);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}
}
