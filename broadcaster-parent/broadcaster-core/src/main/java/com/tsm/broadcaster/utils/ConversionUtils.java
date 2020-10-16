package com.tsm.broadcaster.utils;

import com.tsm.broadcaster.convert.ExtensibleEntityConverter;
import com.tsm.broadcaster.convert.NothingConverter;
import com.tsm.broadcaster.model.Nothing;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionUtils {
    private static final DefaultConversionService conversionService = new DefaultConversionService();

    public ConversionUtils() {
    }

    public static <T> T convert(Object source, Class<T> type) {
        return conversionService.convert(source, type);
    }

    public static void main(String[] args) {
        double value1 = (Double)convert("0", Double.TYPE);
        double value = (Double)convert(Nothing.instance, Double.TYPE);
        System.out.println(value1);
        System.out.println(value);
    }

    static {
        conversionService.addConverter(new NothingConverter());
        conversionService.addConverter(new ExtensibleEntityConverter());
    }
}
