package com.repository.reactive;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.core.RepositoryMetadata;

public class ReactiveRedisRepositoryConfigurationExtensionSupport extends RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return "Redis Reactive";
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return ReactiveRedisRepositoryFactoryBean.class.getName();
    }

    @Override
    protected boolean useRepositoryConfiguration(RepositoryMetadata metadata) {
        return metadata.isReactiveRepository();
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {

        AnnotationAttributes attributes = config.getAttributes();

        builder.addPropertyReference("reactiveRedisOperations", attributes.getString("reactiveRedisTemplateRef"));
        builder.addPropertyReference("hashMapper", attributes.getString("redisHashMapperRef"));
    }
}
