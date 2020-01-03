package com.repository.reactive;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

class ReactiveRedisRepositoryComponentProvider extends ClassPathScanningCandidateComponentProvider {

    public ReactiveRedisRepositoryComponentProvider(Iterable<? extends TypeFilter> includeFilters) {
        super(false);

        Assert.notNull(includeFilters, "Include filters must not be null!");
        //Assert.notNull(registry, "BeanDefinitionRegistry must not be null!");

        //this.registry = registry;

        super.addIncludeFilter(new AnnotationTypeFilter(Repository.class, true, true));

        if (includeFilters.iterator().hasNext()) {
            for (TypeFilter filter : includeFilters) {
                super.addIncludeFilter(filter);
            }
        }
        addExcludeFilter(new AnnotationTypeFilter(NoRepositoryBean.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        //return (metadata.isIndependent() && (metadata.isConcrete() ||
        //    (metadata.isAbstract() && metadata.hasAnnotatedMethods(Lookup.class.getName()))));
        return true;
    }
}
