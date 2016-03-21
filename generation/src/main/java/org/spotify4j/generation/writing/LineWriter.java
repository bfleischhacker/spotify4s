package org.spotify4j.generation.writing;

import java.io.IOException;

public interface LineWriter {
    void writeLine(String line) throws IOException;
}
