package org.mykytainua.simplegameengine.global;

import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

public record AttributeDefinition(
        DataType dataType,
        String name,
        OpenGLBufferType bufferType,
        boolean isInstanced
    ) {
        public DataType getDataType() {
            return this.dataType;
        }
        
        public String getName() {
            return this.name;
        }
        
        public OpenGLBufferType getBufferType() {
            return this.bufferType;
        }
        
        public boolean isInstanced() {
            return this.isInstanced;
        }
    }
