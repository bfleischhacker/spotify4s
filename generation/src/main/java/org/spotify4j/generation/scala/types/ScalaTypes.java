package org.spotify4j.generation.scala.types;

public class ScalaTypes {
    public static final ReferenceType String = new ReferenceType("String", "");
    public static final ReferenceType Int = new ReferenceType("Int", "");
    public static final ReferenceType Double = new ReferenceType("Double", "");
    public static final ReferenceType Boolean = new ReferenceType("Boolean", "");

    public static GenericType Map(Type keyType, Type valueType) {
        return new GenericType("Map", "scala", keyType, valueType);
    }

    public static GenericType List(Type type) {
        return new GenericType("List", "scala", type);
    }

    public static GenericType Option(Type type) {
        return new GenericType("Option", "scala", type);
    }

    public static GenericType Try(Type type) {
        return new GenericType("Try", "scala.util", type);
    }
}
