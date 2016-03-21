package org.spotify4j.generation.scala;

import org.spotify4j.generation.scala.types.ReferenceType;
import org.spotify4j.generation.scala.types.Type;
import org.spotify4j.generation.writing.LineWriter;
import org.spotify4j.generation.writing.PrefixedLineWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScalaMethod {
    public final AccessModifier modifier;
    public final boolean isImplicit;
    public final Type returnType;
    public final String name;
    private List<ScalaField> params;
    private List<ScalaField> implicitParams;
    private List<String> body;
    public final HashSet<Type> references;

    Pattern END_OF_TYPE = Pattern.compile("(:?\\$)([a-zA-Z0-9\\.]+\\.([a-zA-Z0-9]+))");

    public ScalaMethod(AccessModifier modifier, boolean isImplicit, Type returnType, String name) {
        this.modifier = modifier;
        this.isImplicit = isImplicit;
        this.returnType = returnType;
        this.name = name;
        this.params = new ArrayList<>();
        this.implicitParams = new ArrayList<>();
        this.references = new HashSet<>();
    }

    public void write(LineWriter writer) throws IOException {
        writer.writeLine(String.format("def %s%s %s%s%s%s = {",
                isImplicit ? " implicit " : " ",
                modifier == AccessModifier.PUBLIC ? "" : modifier.name().toLowerCase(),
                name,
                params.isEmpty() ? "" :
                        params.stream().map(ScalaField::getDeclaration).collect(Collectors.joining(",", "(", ")")),
                implicitParams.isEmpty() ? "" : implicitParams.stream().map(ScalaField::getDeclaration).collect(Collectors.joining(",", "( implicit ", ")")),
                returnType != null ? " : " + returnType.getDeclaration() : ""
        ));
        final PrefixedLineWriter bodyWriter = new PrefixedLineWriter("  ", writer);
        for (String line : body) {
            bodyWriter.writeLine(line);
        }
        writer.writeLine("}");
    }

    public List<ScalaField> getParams() {
        return params;
    }

    public ScalaMethod withParams(List<ScalaField> params) {
        this.params = params;
        return this;
    }

    public ScalaMethod withImplicitParams(List<ScalaField> implicitParams) {
        this.implicitParams = implicitParams;
        return this;
    }

    public ScalaMethod withBody(List<String> body) {
        this.body = body;
        for (int i = 0; i < this.body.size(); i++) {
            final Matcher matcher = END_OF_TYPE.matcher(body.get(i));
            while (matcher.find()) {
                references.add(new ReferenceType(matcher.group(0), matcher.group(1)));
            }
            this.body.set(i, this.body.get(i).replaceAll(END_OF_TYPE.pattern(), "$1"));
        }

        return this;
    }
}
