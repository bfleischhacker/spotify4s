package org.spotify4j.generation.scala.types;

import java.util.List;

public interface Type {
    String getName();
    String getDeclaration();
    String getPackage();
    List<Type> getChildren();

    ReferenceType boxed();
}
