package org.MykytaInUA.SimpleGameEngine.rendering;

import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

/**
 * Class for storing VBO as array
 */
public class VBOStorage {
	
	private VBOWrapper vbos[];
	
	/**
	 * 
	 * @param size vbo count
	 */
	public VBOStorage(int size) {
		
		this.genBuffers(size);
		
		Utils.checkOpenGLErrors();
	}
	
	public void genBuffers(int size) {
		vbos = new VBOWrapper[size];
		for (int i = 0; i < size; i++) {
			vbos[i] = new VBOWrapper();
		}
	}
	
	/**
	 * Bind VBO to current OpenGL context
	 * @param index the index of VBO in array
	 * (Its not actual OpenGL ID of VBO but 
	 * index in array that stores the ID)
	 */
	public void bindBuffer(int index) {
		vbos[index].bindBuffer();
		Utils.checkOpenGLErrors();
	}
	
	public VBOWrapper getVBOWrapper(int index) {
		return this.vbos[index];
	}
}
