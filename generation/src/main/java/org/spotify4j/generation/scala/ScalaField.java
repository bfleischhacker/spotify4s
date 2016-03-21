package org.spotify4j.generation.scala;

import org.spotify4j.generation.scala.types.ReferenceType;
import org.spotify4j.generation.scala.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScalaField {
    private List<ScalaAnnotation> annotations;
    private String comment;
    private AccessModifier modifier;
    private boolean isFinal;
    private boolean isImplicit;
    private String name;
    private Type type;
    private String assignment;
    private List<ReferenceType> references;

    Pattern END_OF_TYPE = Pattern.compile("(?:\\$)\\(([a-zA-Z0-9\\.]+)\\)\\.([a-zA-Z0-9.]*[a-zA-Z0-9]+)");


    public ScalaField(AccessModifier modifier, boolean isFinal, boolean isImplicit, String name, Type type) {
        this.annotations = new ArrayList<>();
        this.modifier = modifier;
        this.isFinal = isFinal;
        this.isImplicit = isImplicit;
        this.name = name;
        this.type = type;
        this.references = new ArrayList<>();
    }

    public String getDeclaration() {

        return String.format("%s%s%s: %s%s",
                modifier == AccessModifier.PUBLIC ? "" : modifier.name().toLowerCase() + " ",
                modifier != AccessModifier.PUBLIC || !isFinal ? (isFinal ? " val " : "var ") : "",
                name,
                type.getDeclaration(),
                assignment != null ? " = " + assignment : "").replace("  ", " ");

    }

    public String getDefinition() {
        return String.format("%s%s%s%s: %s%s",
                isImplicit ? "implicit " : "",
                modifier == AccessModifier.PUBLIC ? "" : modifier.name().toLowerCase() + " ",
                isFinal ? "val " : "var ",
                name,
                type.getDeclaration(),
                assignment != null ? " = " + assignment : "").replace("  ", " ");
    }

    public AccessModifier getModifier() {
        return modifier;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Type getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public ScalaField withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ScalaField withAssignment(String assignment) {
        this.assignment = assignment;

        final Matcher matcher = END_OF_TYPE.matcher(assignment);
        while (matcher.find()) {
            final String referencedPackageMember = matcher.group(2);
            final String firstPackageOrClass = referencedPackageMember.split("\\.")[0];
            references.add(new ReferenceType(firstPackageOrClass, matcher.group(1)));
        }
        this.assignment = assignment.replaceAll(END_OF_TYPE.pattern(), "$2");

        return this;
    }

    public List<ScalaAnnotation> getAnnotations() {
        return annotations;
    }

    public ScalaField withAnnotations(List<ScalaAnnotation> annotations) {
        this.annotations = annotations;
        return this;
    }

    public List<ReferenceType> getReferences() {
        return references;
    }
}

