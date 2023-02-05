package com.hcmute.yourtours.utils.uuid_projection;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UUIDConvert implements GenericConverter {
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> convertiblePairList = new HashSet<>();
        try {
            convertiblePairList.add(new ConvertiblePair(Class.forName("java.nio.HeapByteBuffer"), UUID.class));
            convertiblePairList.add(new ConvertiblePair(ByteBuffer.class, UUID.class));
            convertiblePairList.add(new ConvertiblePair(byte[].class, UUID.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return convertiblePairList;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source instanceof byte[]) {
            byte[] bytes = (byte[]) source;
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        }
        ByteBuffer byteBuffer = (ByteBuffer) source;
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
