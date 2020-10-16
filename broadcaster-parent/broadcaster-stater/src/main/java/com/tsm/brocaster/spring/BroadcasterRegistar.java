package com.tsm.brocaster.spring;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.tsm.broadcaster.Broadcaster;
import com.tsm.broadcaster.BroadcasterFactory;
import com.tsm.broadcaster.BroadcasterProvider;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;

public class BroadcasterRegistar {
    private Environment environment;
    private BroadcasterBeanNameGenerator nameGenerator = new BroadcasterBeanNameGenerator.Default();

    public BroadcasterRegistar() {
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String broadcasterIds = this.environment.getProperty("broadcaster.id");
        if (!Strings.isNullOrEmpty(broadcasterIds)) {
            String[] ids = broadcasterIds.split(",");
            Arrays.stream(ids).forEach((id) -> {
                this.registerBroadcasterClientBean(registry, id);
            });
        }

    }

    private void registerBroadcasterClientBean(BeanDefinitionRegistry registry, String broadcasterId) {
        String id = broadcasterId.trim();
        Map<String, Object> params = this.getParamsByDoBind(id);
        String provider = this.environment.getRequiredProperty(String.format("broadcaster.%s.provider", id));
        BroadcasterProvider broadcasterProvider = BroadcasterFactory.createBrocasterProvider(provider, params);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Broadcaster.class);
        beanDefinitionBuilder.addConstructorArgValue(broadcasterProvider);
        registry.registerBeanDefinition(this.nameGenerator.generate(id), beanDefinitionBuilder.getBeanDefinition());
    }

    private Map<String, Object> getParamsByDoBind(String broadcasterId) {
        Map<String, Object> params = Maps.newHashMap();
        String oneOfBroadcasterPrefix = String.format("broadcaster.%s", broadcasterId);
        RelaxedDataBinder binder = new RelaxedDataBinder(params, oneOfBroadcasterPrefix);
        binder.bind(new PropertySourcesPropertyValues(((ConfigurableEnvironment)this.environment).getPropertySources(), false));
        return params;
    }
}
