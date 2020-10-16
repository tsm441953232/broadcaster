package com.tsm.brocaster.spring;

import com.tsm.broadcaster.Broadcaster;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass({Broadcaster.class})
@Import({BroadcasterRegistar.class})
public class BroadcastAutoConfiguration {
    public BroadcastAutoConfiguration() {
    }

    @ConditionalOnMissingBean
    public BroadcasterBeanNameGenerator broadcasterBeanNameGenerator() {
        return (broadcastId) -> {
            return broadcastId.concat(Broadcaster.class.getSimpleName());
        };
    }

}
