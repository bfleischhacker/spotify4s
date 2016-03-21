package org.spotify4j.generation.writing;

import java.io.IOException;

public class PrefixedLineWriter implements LineWriter {
    private LineWriter lineWriter;
    private String prefix;

    public PrefixedLineWriter(String prefix, LineWriter lineWriter) {
        this.prefix = prefix;
        this.lineWriter = lineWriter;
    }

    @Override
    public void writeLine(String line) throws IOException {
        lineWriter.writeLine(prefix + line);
    }
}
