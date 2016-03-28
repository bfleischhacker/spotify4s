package org.spotify4j.generation.scala;

import org.spotify4j.generation.Util;
import org.spotify4j.generation.writing.LineWriter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScalaEnum {
    public final String name;
    private List<String> values;

    public ScalaEnum(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public void write(LineWriter writer) throws IOException {
        writer.writeLine(String.format("sealed abstract class %s(val identifier: String) extends Identifiable", name));
        writer.writeLine(String.format("object %s extends Enumerated[%s] {", name, name));
        writer.writeLine(values.stream().map(Util::snakeToCamelCase).collect(Collectors.joining(",", "override val all: Seq[" + name + "] = Seq(", ")")));
        for (String value : values) {
            writer.writeLine(String.format("  object %s extends %s(\"%s\")", Util.snakeToCamelCase(value), name, value));
        }
        writer.writeLine("}");
    }

}
