package org.mykytainua.simplegameengine.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.objects.components.ComponentMetadata;
import org.mykytainua.simplegameengine.utilities.ResizableByteBuffer;

public class LocalDataProvider implements DataProvider{
    
    private final ByteBuffer predefinedSizeDataBuffer; // For components that whose 
                                                  // size is known during initialization
    
    private ResizableByteBuffer resizableBuffer;
    private Map<String, BufferSegment> dynamicBufferSegmentMap;
    
    private final ComponentMetadata metadata;
    
    /**
     * Create an empty local data provider
     * @param dataSizeInBytes
     */
    public LocalDataProvider(ComponentMetadata metadata) {
        this.metadata = metadata;
        int byteCount = this.metadata.getPredefinedParameterBytesCount();
        this.predefinedSizeDataBuffer = ByteBuffer.allocate(byteCount).order(ByteOrder.nativeOrder());
        this.dynamicBufferSegmentMap = new LinkedHashMap<String, BufferSegment>();
        this.resizableBuffer = new ResizableByteBuffer(0, ByteOrder.nativeOrder());
    }
    
    /**
     * Returns read only buffer that contains 
     * all data of an object
     * @param dataSizeInBytes
     */
    public ByteBuffer getAllData() {
        return this.predefinedSizeDataBuffer.asReadOnlyBuffer();
    }

    @Override
    public void setRawData(ByteBuffer data, String parameterName) {  
        if(!metadata.getDataType(parameterName).dataType().isBufferType()) {
            this.setPredefinedSizeData(data, parameterName); 
        } else {
            this.setDynamicSizeData(data, parameterName);
        }
        
    }

    @Override
    public ByteBuffer getRawData(String parameterName) { 
        ByteBuffer data;
        
        if(!metadata.getDataType(parameterName).dataType().isBufferType()) {
            data = this.getPredefinedSizeData(parameterName); 
        } else {
            data = this.getDynamicData(parameterName);
        }
        
        return data;
    }
    
    @Override
    public ByteBuffer getRawData(DataType expectedParameterType, String parameterName) {       
        this.validateParameterType(expectedParameterType, parameterName);       
        return this.getRawData(parameterName);
    }
    
    private void setPredefinedSizeData(ByteBuffer data, String parameterName) {
        int paramPosition = this.metadata.getBytePositionOfPredefinedParameter(parameterName);
        int paramSize = this.metadata.getDataType(parameterName).dataType().getByteSize();
        this.predefinedSizeDataBuffer.put(paramPosition, data, 0, paramSize);
    }
    
    private ByteBuffer getPredefinedSizeData(String parameterName) {
        int paramPosition = this.metadata.getBytePositionOfPredefinedParameter(parameterName);
        int paramSize = this.metadata.getDataType(parameterName).dataType().getByteSize();
        return this.predefinedSizeDataBuffer.slice(paramPosition, paramSize).order(ByteOrder.nativeOrder()).asReadOnlyBuffer();
    }
    
    private void setDynamicSizeData(ByteBuffer data, String parameterName) {
        
        if(this.getBufferSegment(parameterName) == null) { // If data is new
            BufferSegment segment = new BufferSegment(this.resizableBuffer.getLimit(), data.limit());
            this.putBufferSegment(parameterName, segment);
            this.resizableBuffer.append(data);
        } else {
            throw new IllegalArgumentException(String.format("Cannot change data in buffer need implementation"));
        }
    }
    
    private ByteBuffer getDynamicData(String parameterName) {   
        BufferSegment segment = this.getBufferSegment(parameterName);
        
        int position = segment.getBeginning();
        int size = segment.getLength();
        
        return this.resizableBuffer.get(position, size);
    }
    
    private int getDynamicDataSize(String parameterName) {
        DataType datatype = this.metadata.getDataType(parameterName).dataType();
        
        BufferSegment segment = this.getBufferSegment(parameterName);
        
        return datatype.getBufferByteSize(segment.getLength());
    }
    
    private void validateParameterType(DataType expectedParameterType, String parameterName) throws IllegalArgumentException {
        DataType realParameterType = this.metadata.getDataType(parameterName).dataType();
        
        if(expectedParameterType != realParameterType) {
            throw new IllegalArgumentException(String.format(
                    "Parameter '%s' has type %s but expected %s!",
                    parameterName, realParameterType, expectedParameterType
                ));
        }
    }
    
    private BufferSegment getBufferSegment(String parameterName) {
        return this.dynamicBufferSegmentMap.get(parameterName);
    }
    
    private void putBufferSegment(String parameterName, BufferSegment segment) {
        this.dynamicBufferSegmentMap.put(parameterName, segment);
    }
    
    @SuppressWarnings("unused")
    private class BufferSegment {
        private int beginning;
        private int length;
        
        public BufferSegment(int start, int length) {
            this.beginning = start;
            this.length = length;
        }

        public int getBeginning() {
            return beginning;
        }

        public void setBeginning(int start) {
            this.beginning = start;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
        
    }
}
