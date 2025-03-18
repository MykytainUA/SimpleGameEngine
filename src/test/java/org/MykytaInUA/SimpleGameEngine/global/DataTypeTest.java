package org.mykytainua.simplegameengine.global;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataTypeTest {
    
    @Test
    void testDataType() {
        testNonBufferTypes(DataType.BOOLEAN, Byte.BYTES, 1);
        testNonBufferTypes(DataType.BYTE, Byte.BYTES, 1);
        testNonBufferTypes(DataType.SHORT, Short.BYTES, 1);
        testNonBufferTypes(DataType.INT, Integer.BYTES, 1);
        testNonBufferTypes(DataType.LONG, Long.BYTES, 1);
        testNonBufferTypes(DataType.FLOAT, Float.BYTES, 1);
        testNonBufferTypes(DataType.DOUBLE, Double.BYTES, 1);
        testNonBufferTypes(DataType.CHAR, Character.BYTES, 1);

        testNonBufferTypes(DataType.BOOLEAN_VEC2, Byte.BYTES, 2);
        testNonBufferTypes(DataType.BYTE_VEC2, Byte.BYTES, 2);
        testNonBufferTypes(DataType.SHORT_VEC2, Short.BYTES, 2);
        testNonBufferTypes(DataType.INT_VEC2, Integer.BYTES, 2);
        testNonBufferTypes(DataType.LONG_VEC2, Long.BYTES, 2);
        testNonBufferTypes(DataType.FLOAT_VEC2, Float.BYTES, 2);
        testNonBufferTypes(DataType.DOUBLE_VEC2, Double.BYTES, 2);
        testNonBufferTypes(DataType.CHAR_VEC2, Character.BYTES, 2);

        testNonBufferTypes(DataType.BOOLEAN_VEC3, Byte.BYTES, 3);
        testNonBufferTypes(DataType.BYTE_VEC3, Byte.BYTES, 3);
        testNonBufferTypes(DataType.SHORT_VEC3, Short.BYTES, 3);
        testNonBufferTypes(DataType.INT_VEC3, Integer.BYTES, 3);
        testNonBufferTypes(DataType.LONG_VEC3, Long.BYTES, 3);
        testNonBufferTypes(DataType.FLOAT_VEC3, Float.BYTES, 3);
        testNonBufferTypes(DataType.DOUBLE_VEC3, Double.BYTES, 3);
        testNonBufferTypes(DataType.CHAR_VEC3, Character.BYTES, 3);

        testNonBufferTypes(DataType.BOOLEAN_VEC4, Byte.BYTES, 4);
        testNonBufferTypes(DataType.BYTE_VEC4, Byte.BYTES, 4);
        testNonBufferTypes(DataType.SHORT_VEC4, Short.BYTES, 4);
        testNonBufferTypes(DataType.INT_VEC4, Integer.BYTES, 4);
        testNonBufferTypes(DataType.LONG_VEC4, Long.BYTES, 4);
        testNonBufferTypes(DataType.FLOAT_VEC4, Float.BYTES, 4);
        testNonBufferTypes(DataType.DOUBLE_VEC4, Double.BYTES, 4);
        testNonBufferTypes(DataType.CHAR_VEC4, Character.BYTES, 4);

        testNonBufferTypes(DataType.BOOLEAN_MATRIX2, Byte.BYTES, 4);
        testNonBufferTypes(DataType.BYTE_MATRIX2, Byte.BYTES, 4);
        testNonBufferTypes(DataType.SHORT_MATRIX2, Short.BYTES, 4);
        testNonBufferTypes(DataType.INT_MATRIX2, Integer.BYTES, 4);
        testNonBufferTypes(DataType.LONG_MATRIX2, Long.BYTES, 4);
        testNonBufferTypes(DataType.FLOAT_MATRIX2, Float.BYTES, 4);
        testNonBufferTypes(DataType.DOUBLE_MATRIX2, Double.BYTES, 4);
        testNonBufferTypes(DataType.CHAR_MATRIX2, Character.BYTES, 4);

        testNonBufferTypes(DataType.BOOLEAN_MATRIX3, Byte.BYTES, 9);
        testNonBufferTypes(DataType.BYTE_MATRIX3, Byte.BYTES, 9);
        testNonBufferTypes(DataType.SHORT_MATRIX3, Short.BYTES, 9);
        testNonBufferTypes(DataType.INT_MATRIX3, Integer.BYTES, 9);
        testNonBufferTypes(DataType.LONG_MATRIX3, Long.BYTES, 9);
        testNonBufferTypes(DataType.FLOAT_MATRIX3, Float.BYTES, 9);
        testNonBufferTypes(DataType.DOUBLE_MATRIX3, Double.BYTES, 9);
        testNonBufferTypes(DataType.CHAR_MATRIX3, Character.BYTES, 9);

        testNonBufferTypes(DataType.BOOLEAN_MATRIX4, Byte.BYTES, 16);
        testNonBufferTypes(DataType.BYTE_MATRIX4, Byte.BYTES, 16);
        testNonBufferTypes(DataType.SHORT_MATRIX4, Short.BYTES, 16);
        testNonBufferTypes(DataType.INT_MATRIX4, Integer.BYTES, 16);
        testNonBufferTypes(DataType.LONG_MATRIX4, Long.BYTES, 16);
        testNonBufferTypes(DataType.FLOAT_MATRIX4, Float.BYTES, 16);
        testNonBufferTypes(DataType.DOUBLE_MATRIX4, Double.BYTES, 16);
        testNonBufferTypes(DataType.CHAR_MATRIX4, Character.BYTES, 16);
        
        testBufferTypes(DataType.BOOLEAN_BUFFER, Byte.BYTES, 100);
        testBufferTypes(DataType.BYTE_BUFFER, Byte.BYTES, 100);
        testBufferTypes(DataType.SHORT_BUFFER, Short.BYTES, 100);
        testBufferTypes(DataType.INT_BUFFER, Integer.BYTES, 100);
        testBufferTypes(DataType.LONG_BUFFER, Long.BYTES, 100);
        testBufferTypes(DataType.FLOAT_BUFFER, Float.BYTES, 100);
        testBufferTypes(DataType.DOUBLE_BUFFER, Double.BYTES, 100);
        testBufferTypes(DataType.CHAR_BUFFER, Character.BYTES, 100);

    }
       
    private void testNonBufferTypes(DataType type, int baseSize, int multiplier) {
        assertEquals(baseSize * multiplier, type.getByteSize());
        assertEquals(baseSize * multiplier * Byte.SIZE, type.getBitSize());
        assertEquals(false, type.isBufferType());
        assertThrows(UnsupportedOperationException.class, () -> {
            type.getBufferBitSize(100);
        });
    }
    
    private void testBufferTypes(DataType type, int baseSize, int elementCount) {
        assertEquals(true, type.isBufferType());
        assertThrows(UnsupportedOperationException.class, () -> {type.getByteSize();});
        assertThrows(UnsupportedOperationException.class, () -> {type.getBitSize();});
        assertThrows(IllegalArgumentException.class, () -> {type.getBufferByteSize(-1);});
        assertThrows(IllegalArgumentException.class, () -> {type.getBufferBitSize(-1);});
        assertEquals(elementCount * baseSize, type.getBufferByteSize(elementCount));
        assertEquals(elementCount * baseSize * Byte.SIZE, type.getBufferBitSize(elementCount));
    }
}
