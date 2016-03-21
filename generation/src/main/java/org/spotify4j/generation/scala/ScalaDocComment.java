package org.spotify4j.generation.scala;

import org.spotify4j.generation.writing.LineWriter;
import org.spotify4j.generation.writing.PrefixedLineWriter;

import java.io.IOException;
import java.util.List;

public class ScalaDocComment {
    private String description;
    private List<ScalaField> fields;

    public ScalaDocComment(String description, List<ScalaField> fields) {
        this.description = description;
        this.fields = fields;
    }

    public void write(LineWriter lineWriter) throws IOException {
        if((description != null && !description.isEmpty()) || (fields != null && !fields.isEmpty())) {
            PrefixedLineWriter boxWriter = new PrefixedLineWriter(" * ", lineWriter);
            lineWriter.writeLine("/**");
            if (description != null) {
                for (String line : description.split("\n")) {
                    boxWriter.writeLine(line);
                }
            }
            if (fields != null) {
                for (ScalaField field : fields) {
                    String comment = field.getComment() != null ? field.getComment() : "";
                    boxWriter.writeLine(String.format("@param %s %s", field.getName(), comment));
                }
            }
            lineWriter.writeLine(" **/");
        }
    }
}
