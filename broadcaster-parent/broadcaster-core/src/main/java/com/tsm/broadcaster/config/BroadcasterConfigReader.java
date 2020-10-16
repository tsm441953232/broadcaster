package com.tsm.broadcaster.config;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.tsm.broadcaster.exception.BroadcastRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class BroadcasterConfigReader {
    private static Logger logger = LoggerFactory.getLogger(BroadcasterConfigReader.class);

    public BroadcasterConfigReader() {
    }

    public synchronized Map<String, BroadcasterConfig> readConfig() {
        InputStream in = BroadcasterConfigReader.class.getResourceAsStream("/broadcaster.properties");
        if (in == null) {
            throw new BroadcastRuntimeException("无法找到配置文件:broadcaster.properties");
        } else {
            Properties properties = new Properties();

            try {
                properties.load(in);
            } catch (IOException var10) {
                throw new BroadcastRuntimeException("加载配置文件:broadcaster.properties失败");
            }

            Map<String, BroadcasterConfigReader.BroadcasterConfig> broadcasterConfigs = Maps.newHashMap();
            String[] ids = this.getIds(properties);
            logger.info("文件系统配置文件中配置的id列表为:{}", Joiner.on(",").join(ids));
            String[] ids_clone = ids;
            int length = ids.length;

            for(int i = 0; i < length; ++i) {
                String id = ids_clone[i];
                BroadcasterConfigReader.BroadcasterConfig broadcasterConfig = new BroadcasterConfigReader.BroadcasterConfig(id, this.getConfig(properties, id));
                logger.info("Broadcaster id={}，配置为:{}", id, broadcasterConfig);
                broadcasterConfigs.put(id, broadcasterConfig);
            }

            return broadcasterConfigs;
        }
    }

    private String[] getIds(Properties properties) {
        Object provider = properties.get("broadcaster.id");
        if (provider == null) {
            throw new BroadcastRuntimeException("没有配置provider");
        } else {
            return ((String)provider).trim().split(",");
        }
    }

    private Map<String, Object> getConfig(Properties properties, String id) {
        Map<String, Object> params = Maps.newHashMap();
        Iterator iterator = properties.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry)iterator.next();
            String configKey = entry.getKey().toString();
            String prefix = String.format("%s.%s.", "broadcaster", id);
            if (configKey.startsWith(prefix)) {
                String field = configKey.substring(prefix.length());
                params.put(field, entry.getValue());
            }
        }

        if (params.isEmpty()) {
            throw new BroadcastRuntimeException("没有配置id:" + id + "的配置选项");
        } else {
            return params;
        }
    }

    public static class BroadcasterConfig {
        private final String id;
        private final Map<String, Object> configParams;

        public BroadcasterConfig(String id, Map<String, Object> configParams) {
            this.configParams = configParams;
            this.id = id;
        }

        public Map<String, Object> getConfigParams() {
            return this.configParams;
        }

        public String getId() {
            return this.id;
        }

        public String toString() {
            return "BroadcasterConfig{configParams=" + this.configParams + ", id='" + this.id + '\'' + '}';
        }
    }
}
