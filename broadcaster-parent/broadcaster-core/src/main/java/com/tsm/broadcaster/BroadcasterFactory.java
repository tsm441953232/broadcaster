package com.tsm.broadcaster;

import com.tsm.broadcaster.config.BroadcasterConfigReader;
import com.tsm.broadcaster.exception.BroadcastRuntimeException;
import com.tsm.broadcaster.utils.BroadcasterServiceLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class BroadcasterFactory {
    private static Logger logger = LoggerFactory.getLogger(BroadcasterFactory.class);
    private Map<String, BroadcasterConfigReader.BroadcasterConfig> broadcasterConfigs;
    private BroadcasterConfigReader broadcasterConfigReader;

    private BroadcasterFactory() {
        this.broadcasterConfigReader = new BroadcasterConfigReader();
    }

    public static BroadcasterFactory newInstance() {
        return BroadcasterFactory.BroadcasterFactoryHold.INSTANCE;
    }

    public Broadcaster getBroadcaster(String id) {
        logger.debug("获取id={}对应的BroadcasterProvider", id);
        synchronized(this) {
            if (this.broadcasterConfigs == null) {
                this.broadcasterConfigs = this.broadcasterConfigReader.readConfig();
            }
        }

        BroadcasterConfigReader.BroadcasterConfig broadcasterConfig = (BroadcasterConfigReader.BroadcasterConfig)this.broadcasterConfigs.get(id);
        if (broadcasterConfig == null) {
            throw new BroadcastRuntimeException("无法找到指定的id:" + id + "对应的文件系统的配置");
        } else {
            String provider = broadcasterConfig.getConfigParams().get("provider").toString();
            if (provider == null) {
                throw new BroadcastRuntimeException(String.format("id=%s, 没有定义对应的provider", id));
            } else {
                return new Broadcaster(createBrocasterProvider(provider, broadcasterConfig.getConfigParams()));
            }
        }
    }

    public static BroadcasterProvider createBrocasterProvider(String provider, Map<String, Object> configParams) {
        BroadcasterProvider broadcasterProvider = BroadcasterServiceLoaders.getBroadcasterProvider(provider);
        broadcasterProvider.setParams(configParams);
        return broadcasterProvider;
    }

    private static class BroadcasterFactoryHold {
        private static final BroadcasterFactory INSTANCE = new BroadcasterFactory();

        private BroadcasterFactoryHold() {
        }
    }
}
