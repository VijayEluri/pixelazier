package org.jdesktop.tools.io;

import java.io.File;
import java.io.IOException;

public interface FileTreeWalk {
    public void walk(File path) throws IOException;
}
