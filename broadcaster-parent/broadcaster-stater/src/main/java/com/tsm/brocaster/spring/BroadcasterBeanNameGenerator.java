package com.tsm.brocaster.spring;

import com.tsm.broadcaster.Broadcaster;

public interface BroadcasterBeanNameGenerator {
    String generate(String var1);

    public static final class Default implements BroadcasterBeanNameGenerator {
        public Default() {
        }

        public String generate(String broadcastId) {
            return broadcastId.concat(Broadcaster.class.getSimpleName());
        }
    }
}
