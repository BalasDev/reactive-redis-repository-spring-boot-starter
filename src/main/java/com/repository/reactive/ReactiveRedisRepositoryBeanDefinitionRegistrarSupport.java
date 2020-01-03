package com.repository.reactive;

import java.lang.annotation.Annotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationDelegate;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.util.Streamable;

/**
 * AbstractRepositoryConfigurationSourceSupport 7240
 */
public class ReactiveRedisRepositoryBeanDefinitionRegistrarSupport implements ImportBeanDefinitionRegistrar, BeanFactoryAware, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    private Environment environment;

    /**
     * The Spring Data annotation used to enable the particular repository support.
     * @return the annotation class
     */
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveRedisRepositories.class;
    }

    /**
     * The configuration class that will be used by Spring Boot as a template.
     * @return the configuration class
     */
    protected Class<?> getConfiguration() {
        return EnableReactiveRedisRepositoriesConfiguration.class;
    }

    /**
     * The {@link RepositoryConfigurationExtension} for the particular repository support.
     * @return the repository configuration extension
     */
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveRedisRepositoryConfigurationExtensionSupport();
    }

    @EnableReactiveRedisRepositories
    private static class EnableReactiveRedisRepositoriesConfiguration {

    }

    protected Streamable<String> getBasePackages() {
        return Streamable.of(AutoConfigurationPackages.get(this.beanFactory));
    }

    /**
     * The {@link BootstrapMode} for the particular repository support. Defaults to
     * {@link BootstrapMode#DEFAULT}.
     * @return the bootstrap mode
     */
    protected BootstrapMode getBootstrapMode() {
        return BootstrapMode.DEFAULT;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * An auto-configured {@link AnnotationRepositoryConfigurationSource}.
     */
    private class AutoConfiguredAnnotationRepositoryConfigurationSource
        extends AnnotationRepositoryConfigurationSource {

        AutoConfiguredAnnotationRepositoryConfigurationSource(AnnotationMetadata metadata,
                                                              Class<? extends Annotation> annotation, ResourceLoader resourceLoader, Environment environment,
                                                              BeanDefinitionRegistry registry, BeanNameGenerator generator) {
            super(metadata, annotation, resourceLoader, environment, registry, generator);
        }

        @Override
        public Streamable<BeanDefinition> getCandidates(ResourceLoader loader) {

            ReactiveRedisRepositoryComponentProvider scanner = new
                ReactiveRedisRepositoryComponentProvider(getIncludeFilters());
            //todo scanner.setConsiderNestedRepositoryInterfaces(shouldConsiderNestedRepositories());
            scanner.setEnvironment(environment);
            scanner.setResourceLoader(loader);

            getExcludeFilters().forEach(scanner::addExcludeFilter);

            return Streamable.of(() -> getBasePackages().stream()//
                .flatMap(it -> scanner.findCandidateComponents(it).stream()));
        }

        @Override
        public Streamable<String> getBasePackages() {
            return ReactiveRedisRepositoryBeanDefinitionRegistrarSupport.this.getBasePackages();
        }

        @Override
        public BootstrapMode getBootstrapMode() {
            return ReactiveRedisRepositoryBeanDefinitionRegistrarSupport.this.getBootstrapMode();
        }

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry,
                                        BeanNameGenerator importBeanNameGenerator) {
        RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(
            getConfigurationSource(registry, importBeanNameGenerator), this.resourceLoader, this.environment);
        delegate.registerRepositoriesIn(registry, getRepositoryConfigurationExtension());
    }

    private AnnotationRepositoryConfigurationSource getConfigurationSource(BeanDefinitionRegistry registry,
                                                                           BeanNameGenerator importBeanNameGenerator) {
        AnnotationMetadata metadata = AnnotationMetadata.introspect(getConfiguration());
        return new AutoConfiguredAnnotationRepositoryConfigurationSource(metadata, getAnnotation(), this.resourceLoader,
            this.environment, registry, importBeanNameGenerator) {
        };
    }


}
