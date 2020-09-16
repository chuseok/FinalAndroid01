package org.dragon.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

public class CustomBeanNameGenerator  implements BeanNameGenerator {
    private final AnnotationBeanNameGenerator defaultNameGenerator = new AnnotationBeanNameGenerator();

	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {

		String beanName;
		
		if (isService(definition)) {
            beanName = getFullName((AnnotatedBeanDefinition) definition);
        } else {
            beanName = defaultNameGenerator.generateBeanName(definition, registry);
        }

        if (isController(definition)) {
        	beanName = getFullName((AnnotatedBeanDefinition) definition);
		} else {
			beanName = defaultNameGenerator.generateBeanName(definition, registry);
		}

		return beanName;
	}
	
	private Set<String> getAnnotationTypes(final BeanDefinition definition) {
		final AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition) definition;
		final AnnotationMetadata metadata = annotatedDef.getMetadata();
		return metadata.getAnnotationTypes();
	}

	private String getFullName(final AnnotatedBeanDefinition definition) {
        // ��Ű���� ������ ��ü �̸��� ��ȯ�Ѵ�.
        return definition.getMetadata().getClassName();
    }

    private boolean isService(final BeanDefinition definition) {
        if (definition instanceof AnnotatedBeanDefinition) {
            final Set<String> annotationTypes = ((AnnotatedBeanDefinition) definition).getMetadata()
                    .getAnnotationTypes();

            return annotationTypes.stream()
                    .filter(type -> type.equals(Service.class.getName()))
                    .findAny()
                    .isPresent();
        }
        return false;
    }
    
    private boolean isController(final BeanDefinition definition) {
		if (definition instanceof AnnotatedBeanDefinition) {
			
			// definition�� ���� ��� Annotation�� �����ɴϴ�.
			final Set<String> annotationTypes = getAnnotationTypes(definition);
			
			// annotation �� @Controller�̰ų� @RestController �� ��� Controller�� �ν��մϴ�.
			for (final String annotationType : annotationTypes) {
				if (annotationType.equals(Controller.class.getName())) {
					return true;
				}
				if (annotationType.equals(RestController.class.getName())) {
					return true;
				}
			}

		}
		return false;
	}
    
    
}
