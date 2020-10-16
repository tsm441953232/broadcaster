package com.tsm.broadcaster;

import com.tsm.broadcaster.entity.Message;
import com.tsm.broadcaster.utils.BeanSerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Broadcaster {
    private final BroadcasterProvider broadcasterProvider;
    private static Logger logger = LoggerFactory.getLogger(Broadcaster.class);

    public Broadcaster(BroadcasterProvider broadcasterProvider) {
        this.broadcasterProvider = broadcasterProvider;
    }

    public void notify(Message message) {
        try {
            this.broadcasterProvider.notify(message);
        } catch (Throwable var3) {
            logger.error("调用通知服务失败，对应的消息实体为message={}", BeanSerializeUtils.serializeWithNullValueNode(message));
        }

    }
}
