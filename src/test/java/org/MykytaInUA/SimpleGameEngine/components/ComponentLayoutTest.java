package org.mykytainua.simplegameengine.components;

import org.junit.jupiter.api.Test;
import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.components.ComponentLayout;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ComponentLayoutTest {
    
 //   ComponentLayout layout = new ComponentLayout(
//            new AttributeDefinition(DataType.FLOAT_VEC3, "Position", OpenGLBufferType.VBO),
//            new AttributeDefinition(DataType.FLOAT_VEC3, "Rotation", OpenGLBufferType.VBO),
//            new AttributeDefinition(DataType.FLOAT_VEC3, "Size", OpenGLBufferType.VBO),
//            new AttributeDefinition(DataType.BYTE_BUFFER, "Texture", OpenGLBufferType.VBO));
    
//    @Test
//    void testComponentLayout() {
//        // Test if they are equals
//        List<AttributeDefinition> testLayout = new ArrayList<AttributeDefinition>();
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Position"));
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Rotation"));
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Size"));
//        testLayout.add(new AttributeDefinition(DataType.BYTE_BUFFER, "Texture"));
//        
//        assertEquals(true, layout.getConstantSizeParameters().equals(testLayout));
//        
//        // Test if they are not equals
//        testLayout = new ArrayList<AttributeDefinition>();
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Position"));
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Rotation"));
//        testLayout.add(new AttributeDefinition(DataType.FLOAT_VEC3, "Size"));
//        
//        assertEquals(false, layout.getConstantSizeParameters().equals(testLayout));
//    }
}
