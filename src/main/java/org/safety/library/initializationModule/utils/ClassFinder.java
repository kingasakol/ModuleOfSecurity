package org.safety.library.initializationModule.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ClassFinder {
    public List<Class> getAllClasses() throws IOException, ClassNotFoundException, URISyntaxException {
        Enumeration<URL> roots = ClassLoader.getSystemResources("");
        List<Class> result = new LinkedList<>();
        while(roots.hasMoreElements()){
            try{
                String path = Path.of(roots.nextElement().toURI()).toString();
                if(Files.exists(Path.of(path))){
                    File root = new File(path);
                    for(File f: root.listFiles()){
                        List<Class> foundClasses = recursiveClassFind(f, f.getName());
                        foundClasses.forEach(clazz -> result.add(clazz));
                    }
                }
            }
            catch (FileSystemNotFoundException e){}
        }
        return result;
    }

    private List<Class> recursiveClassFind(File root, String packageName) throws ClassNotFoundException {
        List<Class> result = new LinkedList<>();

        if(root.isDirectory()){
            Arrays.stream(root.listFiles()).toList().forEach(file ->{
                try {
                    recursiveClassFind(file,packageName+"."+file.getName()).forEach(foundClass -> {
                        result.add(foundClass);
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        else{
            String name = root.getName();
            if(name.endsWith(".class")){
                result.add(Class.forName(packageName.replaceAll(".class", "")));
            }
        }
        return result;
    }
}
