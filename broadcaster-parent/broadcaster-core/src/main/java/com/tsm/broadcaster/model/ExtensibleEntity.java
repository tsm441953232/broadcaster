package com.tsm.broadcaster.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tsm.broadcaster.utils.ConversionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ExtensibleEntity {
    @JsonIgnore
    Map<String, Object> extension = new HashMap();

    @JsonAnyGetter
    public Map<String, Object> get() {
        return this.extension;
    }

    @JsonAnySetter
    public void set(String key, Object value) {
        if (!this.setPrimitiveProperty(key, value)) {
            this.extension.put(key, value);
        }
    }

    public <T> T get(String nodeCode, Class<T> type) {
        Object result = this.getPrimitiveProperty(nodeCode);
        if (result == null) {
            result = this.getExtendProperty(nodeCode);
        }

        return ConversionUtils.convert(result, type);
    }

    private boolean setPrimitiveProperty(String key, Object value) {
        Field field = ReflectionUtils.findField(this.getClass(), key);
        if (field == null) {
            return false;
        } else {
            field.setAccessible(true);

            try {
                ReflectionUtils.setField(field, this, value);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    private Object getPrimitiveProperty(String nodeCode) {
        try {
            Field field = ReflectionUtils.findField(this.getClass(), nodeCode);
            field.setAccessible(true);
            return ReflectionUtils.getField(field, this);
        } catch (Throwable var3) {
            return null;
        }
    }

    private Object getExtendProperty(String nodeCode) {
        return this.extension.get(nodeCode);
    }

    public ExtensibleEntity() {
    }

    public Map<String, Object> getExtension() {
        return this.extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ExtensibleEntity)) {
            return false;
        } else {
            ExtensibleEntity other = (ExtensibleEntity) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$extension = this.getExtension();
                Object other$extension = other.getExtension();
                if (this$extension == null) {
                    if (other$extension != null) {
                        return false;
                    }
                } else if (!this$extension.equals(other$extension)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExtensibleEntity;
    }

    public int hashCode() {
        int result = 1;
        Object $extension = this.getExtension();
        result = result * 59 + ($extension == null ? 43 : $extension.hashCode());
        return result;
    }

    public String toString() {
        return "ExtensibleEntity(extension=" + this.getExtension() + ")";
    }
}
