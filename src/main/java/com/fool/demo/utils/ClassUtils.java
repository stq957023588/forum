package com.fool.demo.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;

/**
 * @author fool
 * @date 2021/10/18 15:23
 */
public class ClassUtils {

    public static <A extends Annotation> A getAnnotation(AnnotatedElement e, Class<A> annotationClass) {
        A annotation = e.getAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        for (Annotation a : e.getAnnotations()) {
            Class<? extends Annotation> annotationType = a.annotationType();
            if (isMetaAnnotation(annotationType)) {
                continue;
            }
            annotation = annotationType.getAnnotation(annotationClass);
            if (annotation != null) {
                return annotation;
            }
            annotation = getAnnotation(annotationType, annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    public static <T extends Annotation> boolean isAnnotationPresent(AnnotatedElement e, Class<T> annotationClass) {
        if (e.isAnnotationPresent(annotationClass)) {
            return true;
        }
        for (Annotation annotation : e.getAnnotations()) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            if (isMetaAnnotation(aClass)) {
                continue;
            }

            boolean present = isAnnotationPresent(aClass, annotationClass);
            if (present) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMetaAnnotation(Class<?> clazz) {
        return clazz.equals(Documented.class) || clazz.equals(Retention.class) || clazz.equals(Target.class);
    }

}
