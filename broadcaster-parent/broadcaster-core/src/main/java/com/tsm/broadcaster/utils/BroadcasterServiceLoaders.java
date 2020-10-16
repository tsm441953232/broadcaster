package com.tsm.broadcaster.utils;

import com.google.common.base.Preconditions;
import com.tsm.broadcaster.BroadcasterProvider;
import com.tsm.broadcaster.exception.BroadcastRuntimeException;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;

public class BroadcasterServiceLoaders {

    public static BroadcasterProvider getBroadcasterProvider(String provider) {
        Preconditions.checkNotNull(provider != null, "provider不能为空");
        ServiceLoader<BroadcasterProvider> broadcasterProviders = ServiceLoader.load(BroadcasterProvider.class);
        Iterator iterator = broadcasterProviders.iterator();

        while(iterator.hasNext()) {
            BroadcasterProvider broadcasterProvider = (BroadcasterProvider)iterator.next();
            if (Objects.equals(provider, named(broadcasterProvider, BroadcasterProvider.class))) {
                return broadcasterProvider;
            }
        }

        throw new BroadcastRuntimeException(String.format("无法找到provider=%s对应的Provider实现类", provider));
    }

    private static <T> String named(Object obj, Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "clazz不能为空");
        String name = obj.getClass().getSimpleName();
        return name.replace(clazz.getSimpleName(), "").toLowerCase();
    }
}
