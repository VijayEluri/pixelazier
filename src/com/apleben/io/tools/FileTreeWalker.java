package com.apleben.io.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class FileTreeWalker {
    private File path;
    private static final FileFilter directoryFilter = new FileFilter() {
        public boolean accept(File pathName) {
            return pathName.isDirectory();
        }
    };
    private FileFilter filter;

    public FileTreeWalker(File path) throws IOException {
        this(path, new FileFilter() {
            public boolean accept(File pathName) {
                return pathName.isFile();
            }
        });
    }

    public FileTreeWalker(File path, FileFilter filter) throws IOException {
        if (path == null || !path.exists() || path.isFile()) {
            throw new IOException("Path " + path + " is not a valid directory.");
        }
        this.path = path;
        this.filter = filter;
    }

    public void walk(FileTreeWalk walk) throws IOException {
        walkDirectory(walk, path);
    }

    private void walkDirectory(FileTreeWalk walk, File dir) throws IOException {
        File[] files = dir.listFiles(filter);
        for (File file : files) {
            walk.walk(file);
        }

        File[] dirs = dir.listFiles(directoryFilter);
        for (File subDir : dirs) {
            walkDirectory(walk, subDir);
        }
    }
}
