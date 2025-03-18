package org.mykytainua.simplegameengine.objects.components.transform;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.joml.Vector3f;
import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.DataProvider;
import org.mykytainua.simplegameengine.objects.LocalDataProvider;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.ComponentLayout;
import org.mykytainua.simplegameengine.objects.components.ComponentMetadata;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

/**
 * The {@code SizeComponent} class represents the size of a 3D object in terms
 * of its width, height, and depth.
 * 
 * <p>This component is used to store size information and provides
 * functionality to interact with rendering buffers and shaders. It implements
 * the {@link Transform} interface, making it suitable for
 * transformation-related operations within the game engine.</p>
 * 
 */
public class SizeComponent implements Transform,
                                      ShaderComponent{
    
    private static final ComponentMetadata METADATA;
    private final DataProvider rawData;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.FLOAT_VEC3, 
                                        "instanceSize",
                                        OpenGLBufferType.VBO,
                                        true)));
    }

    public static final String ATTRIBUTE_POINTER_NAME = "instanceSize";
    public static final String PREPROCESSOR_DEFINE = "SIZE_COMPONENT";

    /**
     * Constructs a {@code SizeComponent} with the specified size vector.
     *
     * @param size a {@link Vector3f} representing the width, height, and depth of
     *             the object.
     */
    public SizeComponent(Vector3f size) {
        this.rawData = new LocalDataProvider(METADATA);
        this.setSize(size);
    }

    /**
     * Retrieves the size vector.
     *
     * @return a {@link Vector3f} representing the width, height, and depth of the
     *         object.
     */
    public Vector3f getSize() {
        ByteBuffer sizeData = this.getComponentData("instanceSize");
        
        Vector3f size = new Vector3f();
        
        for(int i = 0; i < 3; i++) {
            size.setComponent(i, sizeData.getFloat());
        }
        
        return size;
    }

    /**
     * Updates the size vector.
     *
     * @param size a {@link Vector3f} representing the new width, height, and depth
     *             of the object.
     */
    public void setSize(Vector3f size) {
        ByteBuffer buff = ByteBuffer.allocate(DataType.FLOAT_VEC3.getByteSize()).order(ByteOrder.nativeOrder());
        for(int i = 0; i < 3; i++) {
            buff.putFloat(size.get(i));
        }
        
        buff.flip();
        this.rawData.setRawData(buff, "instanceSize");
    }

    /**
     * Creates a deep copy of this {@code SizeComponent}.
     *
     * @return a new {@code SizeComponent} instance with the same size vector as
     *         this instance.
     */
    @Override
    public Component deepCopy() {
        return new SizeComponent(new Vector3f(this.getSize()));
    }

    @Override
    public String getPreprocessorDefine() {
        return PREPROCESSOR_DEFINE;
    }

    @Override
    public ComponentMetadata getComponentMetadata() {
        return METADATA;
    }
    
    @Override
    public ByteBuffer getComponentData(String name) {       
        return this.rawData.getRawData(name).order(ByteOrder.nativeOrder());
    }
}
