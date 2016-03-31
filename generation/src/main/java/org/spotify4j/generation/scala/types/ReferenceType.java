package org.spotify4j.generation.scala.types;

import java.util.ArrayList;
import java.util.List;

public class ReferenceType implements Type {
    private final String name;
    private final String packageName;

    public ReferenceType(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public static ReferenceType fromQualified(String qualifiedType) {
        final int i = qualifiedType.lastIndexOf(".");
        assert (i > -1);
        return new ReferenceType(qualifiedType.substring(0, i), qualifiedType.substring(i));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDeclaration() {
        return getName();
    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public List<Type> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public ReferenceType boxed() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceType that = (ReferenceType) o;

        if (!name.equals(that.name)) return false;
        return packageName.equals(that.packageName);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        return result;
    }
}
