package com.tsm.broadcaster.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsm.broadcaster.model.ExtensibleEntity;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Set;

public class ExtensibleEntityConverter implements ConditionalGenericConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public ExtensibleEntityConverter() {
    }

    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == targetType.getType()) {
            return false;
        } else {
            try {
                targetType.upcast(ExtensibleEntity.class);
                return true;
            } catch (IllegalArgumentException var4) {
                return false;
            }
        }
    }

    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return this.mapper.convertValue(source, targetType.getType());
    }
}
