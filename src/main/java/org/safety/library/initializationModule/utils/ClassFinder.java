package org.safety.library.initializationModule.utils;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.List;
import java.util.stream.Collectors;

public class ClassFinder {
    public List<Class> getAllClasses(String packageName){
        Reflections reflections = new Reflections(packageName, new SubTypesScanner());
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toList());
    }
}
