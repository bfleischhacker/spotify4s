package org.spotify4j.generation.scala;

import org.spotify4j.generation.scala.types.ReferenceType;
import org.spotify4j.generation.writing.LineWriter;
import org.spotify4j.generation.writing.PrefixedLineWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScalaClass {
    String name;
    String packageName;
    private AccessModifier constructorAccessModifier;
    private ScalaDocComment comment;
    private ScalaClass superClass;
    private List<ScalaField> variables;
    private Set<ScalaEnum> enums;
    private List<ScalaMethod> methods;
    private List<ScalaField> companionFields;
    private List<ScalaMethod> companionMethods;

    public ScalaClass(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.variables = new ArrayList<>();
        this.enums = new HashSet<>();
        this.methods = new ArrayList<>();
        this.companionFields = new ArrayList<>();
        this.companionMethods = new ArrayList<>();
        this.constructorAccessModifier = AccessModifier.PUBLIC;
    }

    public ReferenceType getType() {
        return new ReferenceType(name, packageName);
    }

    private void writePackageDefinition(LineWriter lineWriter) throws IOException {
        lineWriter.writeLine(String.format("package %s", packageName));
    }

    protected Set<String> getPackageDependencies() {
        Set<String> packages = new HashSet<>();
        Stream.concat(
                variables != null ? variables.stream() : Stream.empty(),
                companionFields != null ? companionFields.stream() : Stream.empty())
                .flatMap(v -> Stream.of(
                        v.getAnnotations().stream().map(t -> t.type),
                        Stream.of(v.getType()),
                        v.getType().getChildren().stream(),
                        v.getReferences().stream()
                )).flatMap(Function.identity())
                //filter out our own child enums
                .filter(tpe -> !enums.stream().filter(e -> e.name.equals(tpe.getName())).findAny().isPresent())
                .filter(t -> t.getPackage() != null && !t.getPackage().isEmpty())
                .map(t -> String.format("%s.%s", t.getPackage(), t.getName()))
                .forEach(packages::add);

        Stream.concat(
                methods != null ? methods.stream() : Stream.empty(),
                companionMethods != null ? companionMethods.stream() : Stream.empty())
                .flatMap(m -> Stream.of(
                        m.references.stream(),
                        Stream.of(m.returnType),
                        m.returnType.getChildren().stream(),
                        m.getParams().stream().map(ScalaField::getType)).flatMap(Function.identity()))
                .filter(t -> t != null && t.getPackage() != null && !t.getPackage().isEmpty())
                .map(t -> String.format("%s.%s", t.getPackage(), t.getName()))
                .forEach(packages::add);

        packages.remove("");
        return packages;
    }

    private void writeImports(LineWriter lineWriter) throws IOException {
        for (String importedPackage : getPackageDependencies()) {
            lineWriter.writeLine(String.format("import %s", importedPackage));
        }
    }

    protected String getMemberVariableDeclarations() throws IOException {
        if (variables != null) {
            return variables.stream().map(ScalaField::getDeclaration).collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    protected void writeCompanionObject(LineWriter lineWriter) throws IOException {
        final PrefixedLineWriter prefixedLineWriter = new PrefixedLineWriter("  ", lineWriter);
        if ((companionFields != null && !companionFields.isEmpty()) ||
                (companionMethods != null && !companionMethods.isEmpty()) ||
                (enums != null && !enums.isEmpty())) {
            lineWriter.writeLine(String.format("object %s {", getName()));
            if (companionFields != null) {
                for (ScalaField companionField : companionFields) {
                    prefixedLineWriter.writeLine(String.format("%s", companionField.getDefinition()));
                    lineWriter.writeLine("");
                }
            }

            if (companionMethods != null) {
                lineWriter.writeLine("");
                for (ScalaMethod companionMethod : companionMethods) {
                    companionMethod.write(prefixedLineWriter);
                    lineWriter.writeLine("");
                }
            }

            if (enums != null) {
                for (ScalaEnum anEnum : enums) {
                    anEnum.write(prefixedLineWriter);
                    lineWriter.writeLine("");
                }
            }
            lineWriter.writeLine("}");
        }
    }

    protected String getClassDefinitionName() {
        return name;
    }

    private void writeCaseClassDefinition(LineWriter lineWriter) throws IOException {
        LineWriter indentedLineWriter = new PrefixedLineWriter("  ", lineWriter);

        String extendsModifier = superClass != null ? " extends " + superClass.getName() : "";
        String implementsModifier = "";
        lineWriter.writeLine(String.format("case class %s(%s)%s%s%s",
                getClassDefinitionName(),
                getMemberVariableDeclarations(),
                extendsModifier,
                implementsModifier,
                methods.isEmpty() ? "" : " {"));
        if (!methods.isEmpty()) {
            for (ScalaMethod method : methods) {
                method.write(indentedLineWriter);
            }
            lineWriter.writeLine("}");
        }
        lineWriter.writeLine("");
        writeCompanionObject(lineWriter);

    }

    public void write(LineWriter lineWriter) throws IOException {
        writePackageDefinition(lineWriter);
        lineWriter.writeLine("");
        writeImports(lineWriter);
        lineWriter.writeLine("");
        lineWriter.writeLine("");
        if (comment != null) {
            comment.write(lineWriter);
        }
        writeCaseClassDefinition(lineWriter);
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public ScalaDocComment getComment() {
        return comment;
    }

    public ScalaClass withComment(ScalaDocComment comment) {
        this.comment = comment;
        return this;
    }

    public ScalaClass getSuperClass() {
        return superClass;
    }

    public ScalaClass withSuperClass(ScalaClass superClass) {
        this.superClass = superClass;
        return this;
    }

    public List<ScalaField> getVariables() {
        return variables;
    }

    public ScalaClass withVariables(List<ScalaField> variables) {
        this.variables = variables != null ? variables : new ArrayList<>();
        return this;
    }

    public Set<ScalaEnum> getEnums() {
        return enums;
    }

    public ScalaClass withEnums(Set<ScalaEnum> childClasses) {
        this.enums = childClasses != null ? childClasses : new HashSet<>();
        return this;
    }

    public List<ScalaMethod> getMethods() {
        return methods;
    }

    public ScalaClass withMethods(ArrayList<ScalaMethod> methods) {
        this.methods = methods != null ? methods : new ArrayList<>();
        return this;
    }

    public List<ScalaField> getCompanionFields() {
        return companionFields;
    }

    public List<ScalaMethod> getCompanionMethods() {
        return companionMethods;
    }

    public ScalaClass withCompanionFields(List<ScalaField> companionFields) {
        this.companionFields = companionFields != null ? companionFields : new ArrayList<>();
        return this;
    }

    public ScalaClass withCompanionMethods(List<ScalaMethod> companionMethods) {
        this.companionMethods = companionMethods != null ? companionMethods : new ArrayList<>();
        return this;
    }

    public AccessModifier getConstructorAccessModifier() {
        return constructorAccessModifier;
    }

    public void setConstructorAccessModifier(AccessModifier constructorAccessModifier) {
        this.constructorAccessModifier = constructorAccessModifier;
    }
}
