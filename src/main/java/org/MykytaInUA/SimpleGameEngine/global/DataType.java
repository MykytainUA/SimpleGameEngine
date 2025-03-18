package org.mykytainua.simplegameengine.global;

import static com.jogamp.opengl.GL4.GL_TRUE;
import static com.jogamp.opengl.GL4.GL_FLOAT;
import static com.jogamp.opengl.GL4.GL_UNSIGNED_BYTE;
import static com.jogamp.opengl.GL4.GL_BYTE;
import static com.jogamp.opengl.GL4.GL_SHORT;
import static com.jogamp.opengl.GL4.GL_UNSIGNED_SHORT;
import static com.jogamp.opengl.GL4.GL_UNSIGNED_INT;
import static com.jogamp.opengl.GL4.GL_INT;
import static com.jogamp.opengl.GL4.GL_DOUBLE;

/**
 * Represents different data types used in the engine, including primitive types,
 * vectors, matrices, and buffer types. Buffer types require dynamic size calculation.
 */
public enum DataType {
    
    // Primitive types
    BOOLEAN(BasicType.BOOLEAN, 1, false), 
    BYTE(BasicType.BYTE, 1, false),
    SHORT(BasicType.SHORT, 1, false),
    INT(BasicType.INT, 1, false), 
    LONG(BasicType.LONG, 1, false),
    FLOAT(BasicType.FLOAT, 1, false), 
    DOUBLE(BasicType.DOUBLE, 1, false), 
    CHAR(BasicType.CHAR, 1, false),
    
    // Vector types
    BOOLEAN_VEC2(BasicType.BOOLEAN, 2, false),
    BOOLEAN_VEC3(BasicType.BOOLEAN, 3, false),
    BOOLEAN_VEC4(BasicType.BOOLEAN, 4, false),
    BYTE_VEC2(BasicType.BYTE, 2, false),
    BYTE_VEC3(BasicType.BYTE, 3, false),
    BYTE_VEC4(BasicType.BYTE, 4, false),
    SHORT_VEC2(BasicType.SHORT, 2, false),
    SHORT_VEC3(BasicType.SHORT, 3, false),
    SHORT_VEC4(BasicType.SHORT, 4, false),
    INT_VEC2(BasicType.INT, 2, false), 
    INT_VEC3(BasicType.INT, 3, false), 
    INT_VEC4(BasicType.INT, 4, false),
    LONG_VEC2(BasicType.LONG, 2, false),
    LONG_VEC3(BasicType.LONG, 3, false),
    LONG_VEC4(BasicType.LONG, 4, false),
    FLOAT_VEC2(BasicType.FLOAT, 2, false), 
    FLOAT_VEC3(BasicType.FLOAT, 3, false), 
    FLOAT_VEC4(BasicType.FLOAT, 4, false),
    DOUBLE_VEC2(BasicType.DOUBLE, 2, false), 
    DOUBLE_VEC3(BasicType.DOUBLE, 3, false), 
    DOUBLE_VEC4(BasicType.DOUBLE, 4, false),
    CHAR_VEC2(BasicType.CHAR, 2, false), 
    CHAR_VEC3(BasicType.CHAR, 3, false), 
    CHAR_VEC4(BasicType.CHAR, 4, false),
    
    // Matrix types
    BOOLEAN_MATRIX2(BasicType.BOOLEAN, 4, false), 
    BOOLEAN_MATRIX3(BasicType.BOOLEAN, 9, false), 
    BOOLEAN_MATRIX4(BasicType.BOOLEAN, 16, false),
    BYTE_MATRIX2(BasicType.BYTE, 4, false), 
    BYTE_MATRIX3(BasicType.BYTE, 9, false), 
    BYTE_MATRIX4(BasicType.BYTE, 16, false),
    SHORT_MATRIX2(BasicType.SHORT, 4, false), 
    SHORT_MATRIX3(BasicType.SHORT, 9, false), 
    SHORT_MATRIX4(BasicType.SHORT, 16, false),
    INT_MATRIX2(BasicType.INT, 4, false), 
    INT_MATRIX3(BasicType.INT, 9, false), 
    INT_MATRIX4(BasicType.INT, 16, false),
    LONG_MATRIX2(BasicType.LONG, 4, false), 
    LONG_MATRIX3(BasicType.LONG, 9, false), 
    LONG_MATRIX4(BasicType.LONG, 16, false),
    FLOAT_MATRIX2(BasicType.FLOAT, 4, false), 
    FLOAT_MATRIX3(BasicType.FLOAT, 9, false), 
    FLOAT_MATRIX4(BasicType.FLOAT, 16, false),
    DOUBLE_MATRIX2(BasicType.DOUBLE, 4, false), 
    DOUBLE_MATRIX3(BasicType.DOUBLE, 9, false), 
    DOUBLE_MATRIX4(BasicType.DOUBLE, 16, false),
    CHAR_MATRIX2(BasicType.CHAR, 4, false), 
    CHAR_MATRIX3(BasicType.CHAR, 9, false), 
    CHAR_MATRIX4(BasicType.CHAR, 16, false),
    
    // Basic types
    BOOLEAN_BUFFER(BasicType.BOOLEAN, 1, true), // Size determined at runtime
    BYTE_BUFFER(BasicType.BYTE, 1, true), // Size determined at runtime
    SHORT_BUFFER(BasicType.SHORT, 1, true), // Size determined at runtime
    INT_BUFFER(BasicType.INT, 1, true),  // Size determined at runtime
    LONG_BUFFER(BasicType.LONG, 1, true),  // Size determined at runtime
    FLOAT_BUFFER(BasicType.FLOAT, 1, true),  // Size determined at runtime
    DOUBLE_BUFFER(BasicType.DOUBLE, 1, true),  // Size determined at runtime
    CHAR_BUFFER(BasicType.CHAR, 1, true),  // Size determined at runtime
    
    BOOLEAN_VEC2_BUFFER(BasicType.BOOLEAN, 2, true),
    BOOLEAN_VEC3_BUFFER(BasicType.BOOLEAN, 3, true),
    BOOLEAN_VEC4_BUFFER(BasicType.BOOLEAN, 4, true),
    BYTE_VEC2_BUFFER(BasicType.BYTE, 2, true),
    BYTE_VEC3_BUFFER(BasicType.BYTE, 3, true),
    BYTE_VEC4_BUFFER(BasicType.BYTE, 4, true),
    SHORT_VEC2_BUFFER(BasicType.SHORT, 2, true),
    SHORT_VEC3_BUFFER(BasicType.SHORT, 3, true),
    SHORT_VEC4_BUFFER(BasicType.SHORT, 4, true),
    INT_VEC2_BUFFER(BasicType.INT, 2, true),
    INT_VEC3_BUFFER(BasicType.INT, 3, true),
    INT_VEC4_BUFFER(BasicType.INT, 4, true),
    LONG_VEC2_BUFFER(BasicType.LONG, 2, true),
    LONG_VEC3_BUFFER(BasicType.LONG, 3, true),
    LONG_VEC4_BUFFER(BasicType.LONG, 4, true),
    FLOAT_VEC2_BUFFER(BasicType.FLOAT, 2, true),
    FLOAT_VEC3_BUFFER(BasicType.FLOAT, 3, true),
    FLOAT_VEC4_BUFFER(BasicType.FLOAT, 4, true),
    DOUBLE_VEC2_BUFFER(BasicType.DOUBLE, 2, true),
    DOUBLE_VEC3_BUFFER(BasicType.DOUBLE, 3, true),
    DOUBLE_VEC4_BUFFER(BasicType.DOUBLE, 4, true),
    CHAR_VEC2_BUFFER(BasicType.CHAR, 2, true),
    CHAR_VEC3_BUFFER(BasicType.CHAR, 3, true),
    CHAR_VEC4_BUFFER(BasicType.CHAR, 4, true),

    // Matrix Buffer types
    BOOLEAN_MATRIX2_BUFFER(BasicType.BOOLEAN, 4, true),
    BOOLEAN_MATRIX3_BUFFER(BasicType.BOOLEAN, 9, true),
    BOOLEAN_MATRIX4_BUFFER(BasicType.BOOLEAN, 16, true),
    BYTE_MATRIX2_BUFFER(BasicType.BYTE, 4, true),
    BYTE_MATRIX3_BUFFER(BasicType.BYTE, 9, true),
    BYTE_MATRIX4_BUFFER(BasicType.BYTE, 16, true),
    SHORT_MATRIX2_BUFFER(BasicType.SHORT, 4, true),
    SHORT_MATRIX3_BUFFER(BasicType.SHORT, 9, true),
    SHORT_MATRIX4_BUFFER(BasicType.SHORT, 16, true),
    INT_MATRIX2_BUFFER(BasicType.INT, 4, true),
    INT_MATRIX3_BUFFER(BasicType.INT, 9, true),
    INT_MATRIX4_BUFFER(BasicType.INT, 16, true),
    LONG_MATRIX2_BUFFER(BasicType.LONG, 4, true),
    LONG_MATRIX3_BUFFER(BasicType.LONG, 9, true),
    LONG_MATRIX4_BUFFER(BasicType.LONG, 16, true),
    FLOAT_MATRIX2_BUFFER(BasicType.FLOAT, 4, true),
    FLOAT_MATRIX3_BUFFER(BasicType.FLOAT, 9, true),
    FLOAT_MATRIX4_BUFFER(BasicType.FLOAT, 16, true),
    DOUBLE_MATRIX2_BUFFER(BasicType.DOUBLE, 4, true),
    DOUBLE_MATRIX3_BUFFER(BasicType.DOUBLE, 9, true),
    DOUBLE_MATRIX4_BUFFER(BasicType.DOUBLE, 16, true),
    CHAR_MATRIX2_BUFFER(BasicType.CHAR, 4, true),
    CHAR_MATRIX3_BUFFER(BasicType.CHAR, 9, true),
    CHAR_MATRIX4_BUFFER(BasicType.CHAR, 16, true);

    
    private final BasicType basicType; // Initial type that is stored in data type: byte, boolean, int etc
    private final int elementCount;    // Amount of basic type instances stored in data type
    private final boolean isBuffer;
    
    DataType(BasicType basicType, int elementCount, boolean isBuffer) {
        this.elementCount = elementCount;
        this.basicType = basicType;
        this.isBuffer = isBuffer;
    }
    
    /**
     * Returns the fixed size in bytes for non-buffer types.
     * @throws UnsupportedOperationException if called on buffer types
     */
    public int getByteSize() {      
        return this.basicType.getSIZE_IN_BYTES() * this.elementCount;
    }
    
    /**
     * Returns the size in bits for non-buffer types.
     * @throws UnsupportedOperationException if called on buffer types
     */
    public int getBitSize() {
        return this.getByteSize() * Byte.SIZE;
    }
    
    /**
     * Returns the fixed size in bytes for non-buffer types.
     * @throws UnsupportedOperationException if called on buffer types
     */
    public int getBasicTypeByteSize() {      
        return this.basicType.getSIZE_IN_BYTES();
    }
    
    /**
     * Returns the fixed size in bytes for non-buffer types.
     * @throws UnsupportedOperationException if called on buffer types
     */
    public int getBasicTypeBitSize() {      
        return this.basicType.getSIZE_IN_BYTES();
    }
    
    /**
     * Returns the fixed size in bytes for non-buffer types.
     * @throws UnsupportedOperationException if called on buffer types
     */
    public int getElementsCount() {         
        return this.elementCount;
    }
    
    /**
     * Checks if this DataType represents a buffer.
     */
    public boolean isBufferType() {
        return this.isBuffer; 
    }
    
    public int getOpenGLType() {
        return this.basicType.getOpenGLType(); 
    }
     
    
    /**
     * For buffer types, use this method to calculate the size dynamically.
     *
     * @param elementCount The number of elements in the buffer.
     * @return The total size in bytes.
     */
    public int getBufferByteSize(int elementCount) {
        
        if (!isBufferType()) {
            throw new UnsupportedOperationException(
                "Dynamic size calculation only supported for buffer types. Type: " + this
            );
        }
        if (elementCount < 0) {
            throw new IllegalArgumentException(
                "Element count cannot be negative. Got: " + elementCount
            );
        }
        
        switch (this) {
            case BOOLEAN_BUFFER:
                return elementCount * Byte.BYTES;
            case BYTE_BUFFER:
                return elementCount * Byte.BYTES;
            case SHORT_BUFFER:
                return elementCount * Short.BYTES;
            case INT_BUFFER:
                return elementCount * Integer.BYTES;
            case LONG_BUFFER:
                return elementCount * Long.BYTES;
            case FLOAT_BUFFER:
                return elementCount * Float.BYTES;
            case DOUBLE_BUFFER:
                return elementCount * Double.BYTES;
            case CHAR_BUFFER:
                return elementCount * Character.BYTES;
            default:
                throw new UnsupportedOperationException("Unsupported buffer type: " + this);
        }
    }
    
    /**
     * For buffer types, use this method to calculate the size dynamically.
     *
     * @param elementCount The number of elements in the buffer.
     * @return The total size in bytes.
     */
    public int getBufferBitSize(int elementCount) {      
        return getBufferByteSize(elementCount) * Byte.SIZE;
    }
    
    private enum BasicType {
        BOOLEAN(Byte.BYTES, GL_TRUE), 
        BYTE(Byte.BYTES, GL_BYTE),
        SHORT(Short.BYTES, GL_SHORT),
        INT(Integer.BYTES, GL_INT), 
        LONG(Long.BYTES, GL_INT),
        FLOAT(Float.BYTES, GL_FLOAT), 
        DOUBLE(Double.BYTES, GL_DOUBLE), 
        CHAR(Character.BYTES, GL_UNSIGNED_BYTE);
        
        private final int SIZE_IN_BYTES;
        private final int OPEN_GL_TYPE;
        
        BasicType(int sizeInBytes, int openGLType) {
            this.SIZE_IN_BYTES = sizeInBytes;
            this.OPEN_GL_TYPE = openGLType;
        }
        
        public int getSIZE_IN_BYTES() {
            return this.SIZE_IN_BYTES;
        }
        
        public int getOpenGLType() {
            return this.OPEN_GL_TYPE;
        }
    }
    
}
