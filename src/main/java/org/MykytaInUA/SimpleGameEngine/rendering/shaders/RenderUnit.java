package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL.GL_FLOAT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;
import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;
import org.mykytainua.simplegameengine.rendering.buffers.EBOWrapper;
import org.mykytainua.simplegameengine.rendering.buffers.VAOWrapper;
import org.mykytainua.simplegameengine.rendering.buffers.VBOAttribute;
import org.mykytainua.simplegameengine.rendering.buffers.VBOAttributeStorage;
import org.mykytainua.simplegameengine.rendering.buffers.VBOWrapper;
import org.mykytainua.simplegameengine.utilities.BufferUtils;
import org.mykytainua.simplegameengine.utilities.ResizableByteBuffer;

/**
 * The {@code RenderUnit} class manages the rendering of objects with shared
 * components using a single vao and GPU data accessor.
 */
public class RenderUnit {
    private final Shader boundShader;
    private final VAOWrapper vao;
    private final VBOAttributeStorage vboAttributes;
    private final EBOWrapper ebo; // Element Buffer Object wrapper for managing index data
    private final Mesh mesh; // one mesh per renderUnit
    private int objectsCount = 0;
    
    /**
     * Constructs a new {@code RenderUnit} for rendering objects with the specified shader.
     *
     * @param boundShaderID The ID of the shader program.
     * @param objects       The array of {@code Object3D} instances to be rendered.
     */
    public RenderUnit(Shader boundShader, Object3D object) {
        this.boundShader = boundShader;
        this.mesh = object.getMesh();
        this.vao = new VAOWrapper();
        this.ebo = new EBOWrapper(); 
        this.vboAttributes = new VBOAttributeStorage();
        
        this.vao.bindVertexArray(); 
        this.generateVBOs();
        
        
        this.initializeMesh(this.mesh);
        this.uploadObjectInstanceData(object.getShaderComponents());
        
        this.objectsCount++;
    }

    /**
     * Renders all objects managed by this {@code RenderUnit}.
     */
    public void render() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        this.bindThisRenderUnit();

        gl.glDrawElementsInstanced(
            GL_TRIANGLES,
            this.ebo.getIndicesCount(),
            GL_UNSIGNED_INT,
            0,
            this.objectsCount
        );

        this.unbindBuffers();
    }
    
    /**
     * Initializes mesh data (vertices and indices) in GPU memory for 
     * current render unit, for current implementation mesh is considered
     * being constant per render unit
     */
    public void initializeMesh(Mesh mesh) {        
        this.uploadAttributeData(mesh.getComponentMetadata().getDataType("vertices"), 
                                 mesh.getComponentData("vertices"));
        this.uploadIndicesInstanceData(mesh.getComponentData("indices"));
    }

    /**
     * Adds new objects to this render unit, ensuring they share the same components.
     *
     * @param objects The array of {@code Object3D} instances to add.
     * @throws IllegalArgumentException if the objects' components do not match this
     *                                  {@code RenderUnit}.
     */
    public void addObject(Object3D object) {
        this.uploadObjectInstanceData(object.getShaderComponents());
        this.objectsCount++;
    }
    

    /**
     * Gets the vao wrapper associated with this render unit.
     *
     * @return The {@code VAOWrapper}.
     */
    public VAOWrapper getVAO() {
        return vao;
    }

    /**
     * Gets the number of objects managed by this render unit.
     *
     * @return The object count.
     */
    public int getObjectsCount() {
        return objectsCount;
    }

    /**
     * Binds the vao associated with this render unit.
     */
    private void bindThisRenderUnit() {
        this.vao.bindVertexArray();
    }
    
    public boolean isCompatible(Mesh mesh) {
        return this.mesh.equals(mesh);
    }
    
    public String toString() {
        String RenderUnitrRepresentation = "Render Unit " 
                + Integer.toHexString(System.identityHashCode(this)) + ":" 
                + "\nObjects count:" + this.objectsCount;
        
        return RenderUnitrRepresentation;
    }
    
    /**
     * Gets the ebo wrapper.
     *
     * @return the ebo wrapper instance
     */
    public EBOWrapper getEbo() {
        return ebo;
    }
    
    /**
     * Unbinds all buffers (vbo, VAO, and ebo) to ensure no buffers are left bound.
     */
    public void unbindBuffers() {
        this.unbindVBO(); // Unbind the vbo
        this.unbindVAO(); // Unbind the VAO
        this.unbindEBO(); // Unbind the ebo
    }
    
    /**
     * Unbinds the vbo to ensure no vbo is left bound.
     */
    public void unbindVBO() {
        VBOWrapper.unbindBuffer(); // Unbind the vbo
    }

    /**
     * Unbinds the VAO to ensure no VAO is left bound.
     */
    public void unbindVAO() {
        VAOWrapper.unbindVertexArray(); // Unbind the VAO
    }

    /**
     * Unbinds the ebo to ensure no ebo is left bound.
     */
    public void unbindEBO() {
        EBOWrapper.unbindBuffer(); // Unbind the ebo
    }
    
    private void generateVBOs() {  
        // Process Mesh
        this.generateAttributesFromParameters(mesh.getComponentMetadata().getParameters());
        this.generateAttributesFromParameters(boundShader.getSupportedAttributes());
    }
    
    private void generateAttributesFromParameters(Map<String, AttributeDefinition> parameters) {
        for(Entry<String, AttributeDefinition> entry : parameters.entrySet()) {
            DataType dataType = entry.getValue().dataType();
            String attributeName = entry.getKey();
            
            if(entry.getValue().bufferType() == OpenGLBufferType.VBO) {
                this.vboAttributes.genAttributeBuffer(dataType, attributeName);
            }
        }
    }
    
    private void uploadObjectInstanceData(List<ShaderComponent> components) {
        for (ShaderComponent shaderComponent : components) {
            this.uploadShaderComponentInstanceData(shaderComponent);
        }
        this.unbindBuffers();
    }
    
    private void uploadShaderComponentInstanceData(ShaderComponent shaderComponent) {
        for(Entry<String, AttributeDefinition> parameter : shaderComponent.getComponentMetadata().getParameters().entrySet()) {
            String componentName = parameter.getKey();
            ByteBuffer componentData = shaderComponent.getComponentData(componentName);
            this.uploadAttributeData(parameter.getValue(), componentData);
        }
    }
    
    private void uploadAttributeData(AttributeDefinition attribute, ByteBuffer attributeData) {
        DataType dataType = attribute.getDataType();
        String attributeName = attribute.getName();
        int dataPerVertex = dataType.getElementsCount();
        boolean isInstanced = attribute.isInstanced();
        
        ByteBuffer data = BufferUtils.toDirectByteBuffer(attributeData);
        data.order(ByteOrder.nativeOrder());
        
        VBOAttribute vboAttrib = this.vboAttributes.getAppropriateVBO(dataType, attributeName); 
        
        ByteBuffer GPUData = vboAttrib.getDataFromGPU().order(ByteOrder.nativeOrder());
        
        ByteBuffer updatedBuffer = ByteBuffer.allocateDirect(GPUData.capacity() + data.capacity()).order(ByteOrder.nativeOrder());
        
        updatedBuffer.put(GPUData);
        updatedBuffer.put(data);
        
        updatedBuffer.flip();
        
        vboAttrib.sendVBOData(this.boundShader.getShaderID(), 
                              attributeName, 
                              dataPerVertex, 
                              updatedBuffer, 
                              dataType,
                              isInstanced);
    }
    
    private void uploadIndicesInstanceData(ByteBuffer dataBuffer) {
        IntBuffer indices = BufferUtils.toDirectByteBuffer(mesh.getComponentData("indices")).asIntBuffer();
        this.ebo.setIndicesCount(indices.capacity()); 
        this.ebo.sendStaticIntegerData(indices);
    }
}


