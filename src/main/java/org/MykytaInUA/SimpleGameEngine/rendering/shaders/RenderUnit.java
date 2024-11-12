package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.MykytaInUA.SimpleGameEngine.rendering.EBOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VAOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VBOStorage;
import org.MykytaInUA.SimpleGameEngine.rendering.VBOWrapper;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class RenderUnit {
	
	private int boundShaderID;
	private final VAOWrapper VAO;
	private final VBOStorage VBO;
	private final EBOWrapper EBO;
	
	public RenderUnit(int boundShaderID, int vbosCount) {
		
		this.boundShaderID = boundShaderID;
		
		VAO = new VAOWrapper();
		VBO = new VBOStorage(vbosCount);
		EBO = new EBOWrapper();
	}
	
	public VAOWrapper getVAO() {
		return VAO;
	}
	
	public VBOStorage getVBOStorage() {
		return VBO;
	}
	
	public EBOWrapper getEBO() {
		return EBO;
	}
	
	// render this RenderUnit
	public void render() {
		
	}
	
	
	
	
	// Functions for transferring data
	public void sendVBOFloatVector4Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector4Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector4Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector4InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector4InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector3Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector3Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector3InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector3InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendEBOStaticIntegerData(int renderUnitIndex, int[] dataBuffer) {
		this.getEBO().sendStaticIntegerData(dataBuffer);
	}
	
	public void sendEBOStaticIntegerData(int renderUnitIndex, IntBuffer dataBuffer) {
		this.getEBO().sendStaticIntegerData(dataBuffer);
	}
	
	public void unbindBuffers() {
		this.unbindVBO();
		this.unbindVAO();
		this.unbindEBO();
	}
	
	public void unbindVBO() {
		VBOWrapper.unbindBuffer();
	}
	
	public void unbindVAO() {
		
		VAOWrapper.unbindVertexArray();
		
		Utils.checkOpenGLErrors();
	}

	public void unbindEBO() {
		EBOWrapper.unbindBuffer();
	}
}
