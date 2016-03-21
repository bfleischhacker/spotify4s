package org.spotify4j.generation.writing;

import java.io.IOException;
import java.io.OutputStream;

public class BasicLineWriter implements LineWriter {
    OutputStream outputStream;

    public BasicLineWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String makeOneLine(String str) {
        return str != null ? str.replaceAll("\n+", " ").trim() : null;
    }

    @Override
    public void writeLine(String line) throws IOException {
        outputStream.write(makeOneLine(line).getBytes());
        outputStream.write("\n".getBytes());
    }
}
