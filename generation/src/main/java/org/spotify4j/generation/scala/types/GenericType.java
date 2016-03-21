package org.spotify4j.generation.scala.types;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericType extends ReferenceType {
    private List<Type> children;

    public GenericType(String name, String packageName, Type child, Type... children) {
        super(name, packageName);
        this.children = Stream.concat(Stream.of(child), Stream.of(children))
                .map(Type::boxed)
                .collect(Collectors.toList());
    }

    @Override
    public String getDeclaration() {
            return String.format("%s[%s]", getName(),
                    children.stream().map(Type::getDeclaration).collect(Collectors.joining(", ")));
    }

    @Override
    public List<Type> getChildren() {
        return children;
    }
}
