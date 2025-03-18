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
 * The {@code RotationComponent} class represents the rotation of a 3D object in
 * terms of its pitch, yaw, and roll angles.
 * 
 * <p>This component provides functionality to store, manipulate, and pass
 * rotation data to rendering buffers and shaders. It implements the
 * {@link Transform} interface, making it suitable for transformation-related
 * operations in the game engine.</p>
 * 
 * <p>Instances of this class are used to manage the rotational state of 3D
 * objects in a consistent and efficient manner.</p>
 */
public class RotationComponent implements Transform,
                                          ShaderComponent{
    
    private static final ComponentMetadata METADATA;
    private final DataProvider rawData;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.FLOAT_VEC3, 
                                        "instanceRotation",
                                        OpenGLBufferType.VBO,
                                        true)));
    }

    public static final String ATTRIBUTE_POINTER_NAME = "instanceRotation";
    
    public static final String PREPROCESSOR_DEFINE = "ROTATION_COMPONENT";

    /**
     * Constructs a {@code RotationComponent} with the specified rotation vector.
     *
     * @param rotation a {@link Vector3f} representing the pitch, yaw, and roll
     *                 angles of the object's rotation.
     */
    public RotationComponent(Vector3f rotation) {
        this.rawData = new LocalDataProvider(METADATA);
        this.setRotation(rotation);
    }

    /**
     * Retrieves the rotation vector.
     *
     * @return a {@link Vector3f} representing the pitch, yaw, and roll angles of
     *         the object's rotation.
     */
    public Vector3f getRotation() {
        ByteBuffer rotationData = this.getComponentData("instanceRotation");
        Vector3f rot = new Vector3f();
        
        for(int i = 0; i < 3; i++) {
            rot.setComponent(i, rotationData.getFloat());
        }
        
        return rot;
    }

    /**
     * Updates the rotation vector.
     *
     * @param rotation a {@link Vector3f} representing the new pitch, yaw, and roll
     *                 angles of the object's rotation.
     */
    public void setRotation(Vector3f rotation) {
        ByteBuffer buff = ByteBuffer.allocate(DataType.FLOAT_VEC3.getByteSize()).order(ByteOrder.nativeOrder());
        for(int i = 0; i < 3; i++) {
            buff.putFloat(rotation.get(i));
        }
        
        buff.flip();
        
        
        this.rawData.setRawData(buff, "instanceRotation");
    }

    /**
     * Creates a deep copy of this {@code RotationComponent}.
     *
     * @return a new {@code RotationComponent} instance with the same rotation
     *         vector as this instance.
     */
    @Override
    public Component deepCopy() {
        return new RotationComponent(new Vector3f(this.getRotation()));
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

